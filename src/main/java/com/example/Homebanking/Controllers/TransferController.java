package com.example.Homebanking.controllers;

import com.example.Homebanking.Models.User;
import com.example.Homebanking.Models.Transfer;
import com.example.Homebanking.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;
    @Autowired
    private com.example.Homebanking.services.UserService userService;

    @PostMapping("/execute")
    public ResponseEntity<?> performTransfer(@RequestParam Double amount,
            @RequestParam String destinationDni,
            org.springframework.security.core.Authentication authentication) {

        try {
            // 1. Retrieve the authenticated user's email
            String senderEmail = authentication.getName();

            // 2. Perform the transfer logic using the email
            Transfer transfer = transferService.performTransfer(senderEmail, destinationDni, amount);

            return new ResponseEntity<>("Transfer successful! ID: " + transfer.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // List all transfers 
    @GetMapping("/all")
    public ResponseEntity<List<Transfer>> getAllTransfers() {
        return new ResponseEntity<>(transferService.getAllTransfers(), HttpStatus.OK);
    }

    // Search by Amount
    @GetMapping("/search/amount")
    public ResponseEntity<?> searchByAmount(@RequestParam Double amount) {
        return new ResponseEntity<>(transferService.getTransfersByAmount(amount), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getMyTransfers(Authentication authentication) {
        try {
            List<Transfer> history = transferService.getHistory(authentication.getName());
            return new ResponseEntity<>(history, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
