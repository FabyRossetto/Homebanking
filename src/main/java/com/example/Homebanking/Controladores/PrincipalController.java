
package com.example.Homebanking.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home/principal")
public class PrincipalController {
     @GetMapping("/")
    public String mostrarPrincipal() {
        return "/principal.html"; // Esto redirige a principal.html en la carpeta "resources/templates"
    }
}
