package com.example.Homebanking.Repositories;



import com.example.Homebanking.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Finds a user by email (Unique result expected)
    Optional<User> findByEmail(String email);

    // Finds users by last name (Returns a list because multiple people can have the same last name)
    List<User> findByLastName(String lastName);

    // Finds a user by National ID (DNI)
    Optional<User> findByNationalId(String nationalId);

    // Finds a user based on the ID of their account
    // Spring navigates: User -> Account -> Id
    Optional<User> findByAccount_Id(Long id);
    
    @Query("SELECT u FROM User u WHERE u.debitCard.id = :cardId OR u.creditCard.id = :cardId")
    Optional<User> findOwnerByCardId(@Param("cardId") Long cardId);
}