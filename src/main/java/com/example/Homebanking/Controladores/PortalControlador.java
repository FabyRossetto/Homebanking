package com.example.Homebanking.Controladores;


import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("")
public class PortalControlador {

//    @GetMapping("")
//    public String index() {
//        return "index.html";
//    }
    @GetMapping("")
  public ResponseEntity<Resource> index() throws IOException {
    Resource resource = new ClassPathResource("Templates/index.html"); 
    return ResponseEntity.ok()
            .contentLength(resource.contentLength())
            .contentType(MediaType.TEXT_HTML)
            .body(resource);
}
    @GetMapping("/login")
    public ResponseEntity<Resource> ingreso() throws IOException {
    Resource resource = new ClassPathResource("Templates/ingreso.html"); 
    return ResponseEntity.ok()
            .contentLength(resource.contentLength())
            .contentType(MediaType.TEXT_HTML)
            .body(resource);
}
    
    @PostMapping("/login")
    public ResponseEntity<String> ingreso(@RequestParam String email,@RequestParam String contrasena) {
       
        return ResponseEntity.ok("Datos recibidos: " + email + " y " + contrasena);
    }
    }
     
    
    


