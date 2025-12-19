/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Repositories;

import com.example.Homebanking.Models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    // Finds cards by exact expiration date
    @Query("SELECT c FROM Card c WHERE c.expirationDate = :date")
    List<Card> findByExpirationDate(@Param("date") LocalDate date);
    
    List<Card> findByExpirationDateLessThanEqual(LocalDate date);
}