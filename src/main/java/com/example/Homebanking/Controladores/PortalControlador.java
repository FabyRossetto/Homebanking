package com.example.Homebanking.Controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class PortalControlador {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }
     @GetMapping("/ingreso")
    public String ingreso() {
        return "ingreso";
    }
    
    

}
