
package com.example.Homebanking.Repositories;


import com.example.Homebanking.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Finds an account by its unique number
    Optional<Account> findByNumber(String number);
}