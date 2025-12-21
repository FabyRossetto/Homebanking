/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Homebanking.Controllers;

import com.example.Homebanking.Models.Card;
import com.example.Homebanking.Models.CreditCard;
import com.example.Homebanking.Models.User;
import com.example.Homebanking.Repositories.AccountRepository;
import com.example.Homebanking.Repositories.CardRepository;
import com.example.Homebanking.Repositories.UserRepository;
import com.example.Homebanking.repositories.TransferRepository;
import com.example.Homebanking.services.AccountService;
import com.example.Homebanking.services.CardService;
import com.example.Homebanking.services.UserService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private CardService cardService;

    @GetMapping("/debug-role")
    public ResponseEntity<?> debugMyRole(Authentication authentication) {
        // This will print exactly what Spring Security sees
        return new ResponseEntity<>("Java sees your authorities as: " + authentication.getAuthorities(), HttpStatus.OK);
    }

    // ----------------------------------------------------------------
    // 1. USERS MANAGMENT
    // ----------------------------------------------------------------
    @GetMapping("/users/all")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            userService.hardDeleteUser(id);
            return new ResponseEntity<>("User permanently deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/dni")
    public ResponseEntity<?> findByNationalId(@RequestParam String dni) {
        User user = userService.findByNationalId(dni);
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search/email")
    public ResponseEntity<?> findByEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    // ----------------------------------------------------------------
    // 2. CARDS
    // ----------------------------------------------------------------
    @GetMapping("/cards/expiration")
    public ResponseEntity<?> getCardsByExpiration(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<Card> cards = cardService.findByExpirationDate(date);
        
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

   @DeleteMapping("/cards/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id) {
        try {
            
            cardService.deleteCardAdmin(id);
            return new ResponseEntity<>("Card deleted successfully", HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update Balance 
    @PutMapping("/cards/update-limit")
    public ResponseEntity<?> updateLimit(@RequestParam Long cardId, @RequestParam Double amount) {

        Card card = cardRepository.findById(cardId).orElse(null);

        if (card == null) {
            return new ResponseEntity<>("Card not found", HttpStatus.NOT_FOUND);
        }

        if (card instanceof CreditCard) {

            CreditCard creditCard = (CreditCard) card;

            creditCard.setMaxLimit(amount);

            cardRepository.save(creditCard);

            return new ResponseEntity<>("Credit limit updated to $" + amount, HttpStatus.OK);
        }

        return new ResponseEntity<>("Error: This card is not a Credit Card, cannot update limit.", HttpStatus.BAD_REQUEST);
    }

    // ----------------------------------------------------------------
    // 3. ACCOUNTS
    // ----------------------------------------------------------------
    // Hard Delete (Admin only - requires ID)
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        try {
            accountService.deleteAccount(id);
            return new ResponseEntity<>("Account permanently deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ----------------------------------------------------------------
    // 4. TRANSFERS
    // ----------------------------------------------------------------
    @GetMapping("/transfers/all")
    public ResponseEntity<?> getAllTransfers() {
        return new ResponseEntity<>(transferRepository.findAll(), HttpStatus.OK);
    }
}
