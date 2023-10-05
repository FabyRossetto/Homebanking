package com.example.Homebanking.Controladores;

import org.springframework.web.bind.annotation.GetMapping;

public class PortalControlador {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }
    
    
    
}
