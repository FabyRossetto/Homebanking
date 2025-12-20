package com.example.Homebanking.services;

import com.example.Homebanking.Models.Card;
import com.example.Homebanking.Models.CreditCard;
import com.example.Homebanking.Models.DebitCard;
import com.example.Homebanking.Models.User;
import com.example.Homebanking.Repositories.CardRepository;
import com.example.Homebanking.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    // --- CREATION ---
    @Transactional
    public Card createCard(String email, String type, Integer pin) throws Exception {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (user.getAccount() == null) {
            throw new Exception("You cannot create a card without a bank account.");
        }

        if (pin == null || String.valueOf(pin).length() != 4) {
            throw new Exception("PIN must be 4 digits");
        }

        if (type.equalsIgnoreCase("DEBIT") && user.getDebitCard() != null && user.getDebitCard().isActive()) {
            throw new Exception("User already has an active Debit Card");
        }
        if (type.equalsIgnoreCase("CREDIT") && user.getCreditCard() != null && user.getCreditCard().isActive()) {
            throw new Exception("User already has an active Credit Card");
        }

        Card newCard;

        if (type.equalsIgnoreCase("DEBIT")) {
            newCard = new DebitCard();
            newCard.setBalance(0.0);
        } else if (type.equalsIgnoreCase("CREDIT")) {
            CreditCard creditCard = new CreditCard();
            creditCard.setMaxLimit(500000.00);
            creditCard.setBalance(500000.00);
            newCard = creditCard;
        } else {
            throw new Exception("Invalid card type: Must be DEBIT or CREDIT");
        }

        newCard.setCardHolder(user.getFirstName() + " " + user.getLastName());
        newCard.setPin(pin);
        newCard.setExpirationDate(LocalDate.now().plusYears(5));
        newCard.setActive(true);
        newCard.setNumber(generateCardNumber());
        newCard.setCvv(generateCVV());

        // Save Card first
        cardRepository.save(newCard);

        // Link to User
        if (type.equalsIgnoreCase("DEBIT")) {
            user.setDebitCard(newCard);
        } else {
            user.setCreditCard(newCard);
        }
        userRepository.save(user);

        return newCard;
    }

    // --- MODIFICATION ---
    @Transactional
    public Card updateCard(Long cardId, String email, Integer oldPin, Integer newPin) throws Exception {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new Exception("Card not found"));

        boolean ownsDebit = user.getDebitCard() != null && user.getDebitCard().getId().equals(cardId);
        boolean ownsCredit = user.getCreditCard() != null && user.getCreditCard().getId().equals(cardId);

        if (!ownsDebit && !ownsCredit) {
            throw new Exception("This card does not belong to the authenticated user");
        }

        // Validate Old PIN
        if (!card.getPin().equals(oldPin)) {
            throw new Exception("Old PIN does not match");
        }

        // Update PIN
        if (newPin != null && String.valueOf(newPin).length() == 4) {
            card.setPin(newPin);
        } else {
            throw new Exception("New PIN is invalid");
        }

        return cardRepository.save(card);
    }

    // --- SOFT DELETE ---
    @Transactional
    public void cancelCard(Long cardId, String email) throws Exception {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new Exception("Card not found"));

        boolean ownsDebit = user.getDebitCard() != null && user.getDebitCard().getId().equals(cardId);
        boolean ownsCredit = user.getCreditCard() != null && user.getCreditCard().getId().equals(cardId);

        if (!ownsDebit && !ownsCredit) {
            throw new Exception("Access Denied: You do not own this card");
        }

        card.setActive(false);
        cardRepository.save(card);
    }

    // --- HARD DELETE ---
    @Transactional
    public void deleteCard(String email, Long cardId) throws Exception {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new Exception("Card not found"));

        if (user.getDebitCard() != null && user.getDebitCard().getId().equals(cardId)) {
            user.setDebitCard(null);
        } else if (user.getCreditCard() != null && user.getCreditCard().getId().equals(cardId)) {
            user.setCreditCard(null);
        } else {

            throw new Exception("Access Denied: You cannot delete a card that isn't linked to you");
        }

        userRepository.save(user); // Save user without the card
        cardRepository.delete(card); // Now delete the card
    }

    // --- SEARCH METHODS ---
    public Card findById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    public List<Card> findCardsByUser(String email) throws Exception {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        List<Card> cards = new ArrayList<>();
        if (user.getDebitCard() != null) {
            cards.add(user.getDebitCard());
        }
        if (user.getCreditCard() != null) {
            cards.add(user.getCreditCard());
        }

        return cards;
    }

    public List<Card> findByExpirationDate(LocalDate date) {
        LocalDate fechaFin = date.plusDays(1);
        return cardRepository.findByExpirationDateBetween(date, fechaFin);
    }

    // --- UTILS ---
    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int section = random.nextInt(9000) + 1000;
            number.append(section).append(i < 3 ? "-" : "");
        }
        return number.toString();
    }

    private Integer generateCVV() {
        return new Random().nextInt(900) + 100;
    }
}
