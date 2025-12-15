package com.example.homebanking.Models;

import com.example.Homebanking.Models.Account;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @ManyToOne 
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;

    private LocalDateTime date;

    @Column(nullable = false)
    private Double amount;
    
    private String description; 

    public Transfer() {
    }

    public Transfer(Account sourceAccount, Account destinationAccount, Double amount, String description) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        this.description = description;
        this.date = LocalDateTime.now();
    }
}