package com.example.Homebanking.controladores;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("")
public class PortalControlador {

 @GetMapping("/login")//me devuelve el string,no el html
    public String ingreso() {
        return "login";
    }
  
    }
     
    
    


