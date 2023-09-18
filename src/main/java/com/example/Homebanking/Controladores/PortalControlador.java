package com.example.Homebanking.Controladores;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("")
public class PortalControlador {

    @GetMapping("")
    public String index() {
        return "index.html";
    }
    @GetMapping("/login")
    public String ingreso(){
        return"ingreso.html";
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> ingreso(@RequestParam String email,@RequestParam String contrasena) {
       
        return ResponseEntity.ok("Datos recibidos: " + email + " y " + contrasena);
    }
    }
     
    
    


