package com.example.Homebanking.Controladores;


import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Repositorios.UsuarioRepositorio;
import com.example.Homebanking.Servicios.UsuarioServicio;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/usuarios")
public class PortalControlador {
    
    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    UsuarioServicio usuarioServicio;
    
    @GetMapping()
    public List<Usuario> listaUsuarios(){
    return usuarioRepositorio.findAll();
    }
    
     @GetMapping("/usuario")
    public ResponseEntity<Usuario> usuario() {
        return ResponseEntity.ok(usuarioRepositorio.findByEmail("sabri@hola.com"));
    }


//    @GetMapping("")
//    public String index() {
//        return "index.html";
//    }
   /* @GetMapping("")
  public ResponseEntity<Resource> index() throws IOException {
    Resource resource = new ClassPathResource("Templates/index.html"); 
    return ResponseEntity.ok()
            .contentLength(resource.contentLength())
            .contentType(MediaType.TEXT_HTML)
            .body(resource);
}
   /* @GetMapping("/login")
    public ResponseEntity<Resource> ingreso() throws IOException {
    Resource resource = new ClassPathResource("Templates/ingreso.html"); 
    return ResponseEntity.ok()
            .contentLength(resource.contentLength())
            .contentType(MediaType.TEXT_HTML)
            .body(resource);
}
    
    /*@PostMapping("/login")
    public ResponseEntity<String> ingreso(@RequestParam String email,@RequestParam String contrasena) {
       
        return ResponseEntity.ok("Datos recibidos: " + email + " y " + contrasena);
    }*/
  
    }
     
    
    

