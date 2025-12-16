package com.example.Homebanking.services;

import com.example.Homebanking.Models.Account;
import com.example.Homebanking.Models.Transfer;
import com.example.Homebanking.Models.User;
import com.example.Homebanking.repositories.TransferRepository;
import com.example.Homebanking.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private UserRepository userRepository;

  
    @Autowired
    private NotificationService notificationService;

    private static final Double MIN_AMOUNT = 1000.0;
    private static final Double MAX_AMOUNT = 300000.0;

    // --- MAIN TRANSACTIONAL METHOD ---
    @Transactional
    public Transfer performTransfer(String senderEmail, String destinationNationalId, Double amount) throws Exception {
        
        // 1. Retrieve Sender User and Account 
        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new Exception("Sender user not found"));

        
        User receiver = validateTransferAndRetrieveReceiver(sender, destinationNationalId, amount); 
        
        Account sourceAccount = sender.getAccount();
        Account destinationAccount = receiver.getAccount();

        // 3. Execute Money Movement
        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);
       

        // 4. Create and Save Transfer Record
        Transfer transfer = new Transfer();
        transfer.setAmount(amount);
        transfer.setSourceAccount(sourceAccount);
        transfer.setDestinationAccount(destinationAccount);
        transfer.setDate(LocalDateTime.now());
        transfer.setDescription("Transfer to " + receiver.getLastName());

        transferRepository.save(transfer);

        // 5. Send Notification
        notificationService.sendEmail(sender.getEmail(), 
                "Transfer Successful", 
                "You have successfully transferred $" + amount + " to " + receiver.getFirstName() + " " + receiver.getLastName());

        return transfer;
    }

    // --- VALIDATION LOGIC  ---
    private User validateTransferAndRetrieveReceiver(User sender, String destinationNationalId, Double amount) throws Exception {
        
        // Check 1: Nulls
        if (destinationNationalId == null || destinationNationalId.isEmpty()) {
            throw new Exception("Destination National ID (DNI) is required");
        }

        // Check 2: DNI Length 
        if (destinationNationalId.length() < 7) {
            throw new Exception("Invalid National ID format");
        }

        // Check 3: Self-transfer
        if (sender.getNationalId().equalsIgnoreCase(destinationNationalId)) {
            throw new Exception("You cannot transfer money to yourself using this method");
        }
        
        // Check 4 & 5: Receiver Existence and Account Existence (OPTIMIZED: only one DB call)
        User receiver = userRepository.findByNationalId(destinationNationalId)
             .orElseThrow(() -> new Exception("User with the provided National ID does not exist"));

        if (sender.getAccount() == null) {
            throw new Exception("Sender does not have an active account");
        }
        
        if (receiver.getAccount() == null) {
            throw new Exception("The destination user does not have an active account");
        }

        // Check 6: Balance and Limits
        if (amount > sender.getAccount().getBalance()) {
            throw new Exception("Insufficient funds");
        }
        
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new Exception("Amount must be between $" + MIN_AMOUNT + " and $" + MAX_AMOUNT);
        }
        
        return receiver;
    }

    // --- SEARCH METHODS ---
    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }

    public List<Transfer> getTransfersByAmount(Double amount) {
        return transferRepository.findByAmount(amount);
    }

    public List<Transfer> getTransfersByDate(int year, int month, int day) {
        
        LocalDateTime startOfDay = LocalDate.of(year, month, day).atStartOfDay();
        LocalDateTime endOfDay = LocalDate.of(year, month, day).atTime(23, 59, 59);
        
        return transferRepository.findByDateBetween(startOfDay, endOfDay);
    }
}