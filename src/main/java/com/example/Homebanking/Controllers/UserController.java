package com.example.Homebanking.controllers;

import com.example.Homebanking.Models.User;
import com.example.Homebanking.Security.JwtService;
import com.example.Homebanking.Services.AuthResponse;
import com.example.Homebanking.Services.LoginRequest;
import com.example.Homebanking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            User user = userService.findByEmail(request.getEmail());
            String jwtToken = jwtService.generateToken(user);
            return ResponseEntity.ok(new AuthResponse(jwtToken));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String nationalId) {
        try {
            User newUser = userService.registerUser(firstName, lastName, email, password, nationalId);
            return new ResponseEntity<>("User registered successfully. ID: " + newUser.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/me/update")
    public ResponseEntity<?> updateUser(Authentication authentication, 
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String nationalId) {
        try {
            userService.updateUser(authentication.getName(), firstName, lastName, nationalId);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/password/request")
    public ResponseEntity<?> requestRecoveryCode(@RequestParam String email) {
        try {
            userService.sendRecoveryCode(email);
            return new ResponseEntity<>("Recovery code sent to your email.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(@RequestParam String email,
            @RequestParam String code,
            @RequestParam String newPassword) {
        try {
            userService.changePassword(email, code, newPassword);
            return new ResponseEntity<>("Password changed successfully! You can now log in.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/me/deactivate")
    public ResponseEntity<?> deactivateUser(Authentication authentication) {
        try {
            userService.softDeleteUser(authentication.getName());
            return new ResponseEntity<>("User deactivated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            userService.hardDeleteUser(id);
            return new ResponseEntity<>("User permanently deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/dni")
    public ResponseEntity<?> findByNationalId(@RequestParam String dni) {
        User user = userService.findByNationalId(dni);
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search/email")
    public ResponseEntity<?> findByEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}