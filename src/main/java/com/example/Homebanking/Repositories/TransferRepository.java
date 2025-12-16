package com.example.Homebanking.repositories;

import com.example.Homebanking.Models.Account;
import com.example.Homebanking.Models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    // Finds transfers by exact amount
    List<Transfer> findByAmount(Double amount);

    // Finds transfers by exact date and time
    // Note: This matches the exact timestamp (down to the millisecond)
    List<Transfer> findByDate(LocalDateTime date);
    
    //  Finds transfers between two dates
    List<Transfer> findByDateBetween(LocalDateTime start, LocalDateTime end);
    
    List<Transfer> findBySourceAccount(Account account);
    List<Transfer> findByDestinationAccount(Account account);
}