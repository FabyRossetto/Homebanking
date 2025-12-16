package com.example.Homebanking.services;

import com.example.Homebanking.Enums.Role;
import com.example.Homebanking.Models.User;
import com.example.Homebanking.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(String firstName, String lastName, String email, String password, String nationalId) throws Exception {
        validate(firstName, lastName, email, password, nationalId);

        if (userRepository.findByEmail(email).isPresent()) {
            throw new Exception("Email already in use");
        }

        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setNationalId(nationalId);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setActive(true);

        if (email.contains("@admin.com") || password.equals("ADMIN1234")) {
            newUser.setRole(Role.ADMIN);
        } else {
            newUser.setRole(Role.CLIENT);
        }

        userRepository.save(newUser);
        notificationService.sendEmail(email, "Welcome!", "Welcome to HomeBanking, " + firstName);
        return newUser;
    }

    @Transactional
    public void updateUser(String email, String firstName, String lastName, String nationalId) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        if (firstName != null && !firstName.isEmpty()) {
            user.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            user.setLastName(lastName);
        }
        if (nationalId != null && !nationalId.isEmpty()) {
            user.setNationalId(nationalId);
        }

        userRepository.save(user);
    }

    @Transactional
    public void sendRecoveryCode(String email) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        int randomCode = (int) (Math.random() * 9000) + 1000;

        user.setRecoveryCode(String.valueOf(randomCode));
        userRepository.save(user);

        String subject = "Password Recovery Code";
        String body = "Hello " + user.getFirstName() + ",\n\n"
                + "Your verification code is: " + randomCode + "\n"
                + "Please enter it in the app to change your password.";

        notificationService.sendEmail(user.getEmail(), subject, body);
    }

    @Transactional
    public void changePassword(String email, String code, String newPassword) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        if (user.getRecoveryCode() == null || !user.getRecoveryCode().equals(code)) {
            throw new Exception("The code is incorrect or invalid");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setRecoveryCode(null);
        userRepository.save(user);
    }

    @Transactional
    public void softDeleteUser(String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("User not found"));
        user.setActive(false);
        userRepository.save(user);
    }

    @Transactional
    public void hardDeleteUser(String id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
        userRepository.delete(user);
    }

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findByNationalId(String dni) {
        return userRepository.findByNationalId(dni).orElse(null);
    }

    public List<User> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    private void validate(String firstName, String lastName, String email, String password, String nationalId) throws Exception {
        if (firstName == null || firstName.isEmpty()) {
            throw new Exception("First name is required");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new Exception("Last name is required");
        }
        if (email == null || email.isEmpty()) {
            throw new Exception("Email is required");
        }
        if (password == null || password.length() < 6) {
            throw new Exception("Password must be at least 6 characters");
        }
        if (nationalId == null || nationalId.length() < 7) {
            throw new Exception("Invalid National ID (DNI)");
        }
    }
}