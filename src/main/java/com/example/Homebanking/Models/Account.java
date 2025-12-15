
package com.example.Homebanking.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Data;



@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number; 

    private Double balance; 

    private LocalDateTime creationDate;

    private boolean active = true;

  
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User owner;

    public Account() {
    }

    public Account(String number, Double balance, LocalDateTime creationDate, User owner) {
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
        this.owner = owner;
    }
}