
package com.example.Homebanking.Models;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "card_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Card { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardHolder; 

    private String number; 

    private Integer cvv;

    private Double balance; 

    @Column(nullable = false)
    private Integer pin;

    private LocalDate expirationDate; 

    private boolean active = true;

    public Card() {
    }
    
   
    public Card(String cardHolder, String number, Integer cvv, Integer pin, LocalDate expirationDate) {
        this.cardHolder = cardHolder;
        this.number = number;
        this.cvv = cvv;
        this.pin = pin;
        this.expirationDate = expirationDate;
    }
}