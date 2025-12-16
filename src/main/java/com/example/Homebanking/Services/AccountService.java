package com.example.Homebanking.services;

import com.example.Homebanking.Models.Account;
import com.example.Homebanking.Models.User;
import com.example.Homebanking.Repositories.AccountRepository;
import com.example.Homebanking.Repositories.UserRepository;
import com.example.Homebanking.Models.Transfer;

import com.example.Homebanking.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransferRepository transferRepository;

    // --- CREATE ACCOUNT  ---
    @Transactional
    public Account createAccount(String email) throws Exception {
        // 1. Find user by Email 
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        // 2. Validate if user already has an account 
        if (user.getAccount() != null) {
            throw new Exception("User already has an account");
        }

        // 3. Generate random VIN number
        String accountNumber = "VIN-" + (int) ((Math.random() * (99999999 - 10000000)) + 10000000);

        Account account = new Account();
        account.setNumber(accountNumber);
        account.setBalance(0.0);
        account.setCreationDate(LocalDateTime.now());
        account.setActive(true);

        // 4. Save account
        accountRepository.save(account);

        // 5. Link User -> Account and Account -> User
        user.setAccount(account);
        account.setOwner(user);

        userRepository.save(user);
        return accountRepository.save(account);
    }

    // --- SOFT DELETE (Cancel my own account) ---
    @Transactional
    public void cancelAccount(String email) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        Account account = user.getAccount();
        
        if (account == null) {
            throw new Exception("No account found for this user");
        }

        account.setActive(false);
        accountRepository.save(account);
    }

    // --- HARD DELETE (Admin only - Keeps ID) ---
    @Transactional
    public void deleteAccount(Long accountId) throws Exception {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new Exception("Account not found"));

        // Delete sent transfers
        List<Transfer> sent = transferRepository.findBySourceAccount(account);
        transferRepository.deleteAll(sent);

        // Delete received transfers
        List<Transfer> received = transferRepository.findByDestinationAccount(account);
        transferRepository.deleteAll(received);

        // Unlink from owner
        User owner = account.getOwner();
        if (owner != null) {
            owner.setAccount(null);
            userRepository.save(owner);
        }

        // Delete permanently
        accountRepository.delete(account);
    }

    // --- TRANSACTIONS (Deposit to my account) ---
    @Transactional
    public void deposit(String email, Double amount) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        Account account = user.getAccount();

        if (account == null) {
             throw new Exception("Account not found");
        }

        if (!account.isActive()) {
            throw new Exception("Account is inactive");
        }

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    // --- TRANSACTIONS (Withdraw from my account) ---
    @Transactional
    public void withdraw(String email, Double amount) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        Account account = user.getAccount();

        if (account == null) {
            throw new Exception("Account not found");
        }

        if (!account.isActive()) {
            throw new Exception("Account is inactive");
        }

        if (amount > account.getBalance()) {
            throw new Exception("Insufficient funds");
        }
        
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    // --- FINDERS ---
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number).orElse(null);
    }
}