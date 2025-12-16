
package com.example.Homebanking.Models;

import com.example.Homebanking.Enums.Role;
import com.example.Homebanking.Models.Card;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Data
@Table(name = "users") 
public class User implements UserDetails{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String nationalId; 
    
    @Column(name = "recovery_code")
    private String recoveryCode;

    
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private Account account;

    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "debit_card_id")
    private Card debitCard;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_card_id")
    private Card creditCard;

    private boolean active = true; 

    private LocalDateTime createdAt; 

    @Enumerated(EnumType.STRING)
    private Role role;
    
   
    public User() { 
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
     
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email; 
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; 
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; 
    }

    @Override
    public boolean isEnabled() {
        return active; 
    }

    public void setActive(boolean active) {
    this.active = active; 
}
}
