package com.example.Homebanking.Controllers;

import com.example.Homebanking.Models.Account;
import com.example.Homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Create Account (Auto-detect user)
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(Authentication authentication) {
        try {
            Account newAccount = accountService.createAccount(authentication.getName());
            return new ResponseEntity<>("Account created successfully with ID: " + newAccount.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating account: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Deposit (Auto-detect user)
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(Authentication authentication, @RequestParam Double amount) {
        try {
            accountService.deposit(authentication.getName(), amount);
            return new ResponseEntity<>("Deposit successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Withdraw (Auto-detect user)
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(Authentication authentication, @RequestParam Double amount) {
        try {
            accountService.withdraw(authentication.getName(), amount);
            return new ResponseEntity<>("Withdrawal successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Soft Delete / Cancel (Auto-detect user)
    @PatchMapping("/me/deactivate")
    public ResponseEntity<?> cancelMyAccount(Authentication authentication) {
        try {
            accountService.cancelAccount(authentication.getName());
            return new ResponseEntity<>("Account deactivated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    
}
