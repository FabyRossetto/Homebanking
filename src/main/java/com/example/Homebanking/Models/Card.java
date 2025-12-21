
package com.example.Homebanking.Models;


import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
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
    
    
    public Long getId() {
        return id;
    }
    
    
    @Transient 
    public String getType() {
        if (this instanceof CreditCard) {
            return "CREDIT";
        } else {
            return "DEBIT"; 
        }
    }
    
    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", cardHolder='" + cardHolder + '\'' +
                '}';
    }
}