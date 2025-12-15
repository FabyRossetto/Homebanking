package com.example.homebanking.controllers;

import com.example.homebanking.Models.Card;
import com.example.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    // Create Card (Unified for Debit and Credit)
    @PostMapping("/create")
    public ResponseEntity<?> createCard(Authentication authentication, 
                                        @RequestParam String type, 
                                        @RequestParam Integer pin) {
        try {
            // Get email from Token
            String email = authentication.getName();
            
            Card card = cardService.createCard(email, type, pin);
            
            return new ResponseEntity<>("Card created successfully: " + card.getNumber(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update PIN 
    @PutMapping("/update-pin")
    public ResponseEntity<?> updateCardPin(Authentication authentication, 
                                           @RequestParam Long cardId, 
                                          
                                           @RequestParam Integer oldPin, 
                                           @RequestParam Integer newPin) {
        try {
            // Pass email from token to service
            cardService.updateCard(cardId, authentication.getName(), oldPin, newPin);
            return new ResponseEntity<>("Card PIN updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update Balance 
    @PutMapping("/update-limit") 
    public ResponseEntity<?> updateLimit(@RequestParam Long cardId, @RequestParam Double amount) {
       
        return new ResponseEntity<>("Limit updated ", HttpStatus.OK);
    }

    // Soft Delete 
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateCard(Authentication authentication, @PathVariable Long id) {
        try {
            // Pass email from token to service for ownership check
            cardService.cancelCard(id, authentication.getName());
            return new ResponseEntity<>("Card deactivated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Hard Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCard(Authentication authentication, @PathVariable Long id) {
        try {
             // Pass email from token to service for ownership check
            cardService.deleteCard(authentication.getName(), id);
            return new ResponseEntity<>("Card deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping("/current") 
    public ResponseEntity<?> getMyCards(Authentication authentication) {
        try {
            // Uses email from token
            List<Card> cards = cardService.findCardsByUser(authentication.getName());
            return new ResponseEntity<>(cards, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Search by Expiration Date 
    @GetMapping("/expiration")
    public ResponseEntity<?> getCardsByExpiration(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return new ResponseEntity<>(cardService.findByExpirationDate(date), HttpStatus.OK);
    }
}