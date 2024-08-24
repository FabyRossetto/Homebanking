package com.example.Homebanking.controladores;

import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Entidades.UsuarioLogin;
import com.example.Homebanking.Errores.ErrorServicio;
import com.example.Homebanking.Repositorios.UsuarioRepositorio;
import com.example.Homebanking.Servicios.UsuarioServicio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@CrossOrigin(origins = "null")
@RequestMapping("/usuario")//localhost:8080/usuario
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio uSer;
    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @GetMapping("/mostrar")
    public String mostrarPagina(Model model) {
        model.addAttribute("usuarioRegistro", new Usuario());
        model.addAttribute("usuarioLogin", new Usuario());
        return "principal";
    }

    @PostMapping("/registro")
    public Object registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        System.out.println("Recibida solicitud de registro.");

        boolean registroExitoso = intentarRegistro(usuario);

        if (registroExitoso) {
            // Registro exitoso, redirigir a la página registro_exitoso.html
            return "registro_exitoso";
        } else {
            // Si el registro falla, devolver un ResponseEntity con un estado HTTP 400 (Bad Request) u otro adecuado
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en el registro :C");
        }
    }

    private boolean intentarRegistro(Usuario usuario) {
        try {
            uSer.crear(usuario.getNombre(), usuario.getApellido(), usuario.getDNI(), usuario.getEmail(), usuario.getClave());
            return true;
        } catch (Exception e) {
            // Imprimir la traza de la pila en la consola
            e.printStackTrace();
        }
        return false;

    }

    @GetMapping("/login")
    public String mostrarPaginaLogin(Model model) {
        model.addAttribute("usuarioLogin", new Usuario());
        model.addAttribute("usuarioRegistro", new Usuario());
        return "principal";
    }

    @PostMapping("/logincheck")
    public String procesarLogin(@ModelAttribute("usuarioLogin") UsuarioLogin usuarioLogin, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Intentar autenticar al usuario
            Usuario usuarioAutenticado = uSer.autenticarUsuario(usuarioLogin.getEmail(), usuarioLogin.getClave());

            // Si no se lanzó una excepción, el usuario se autenticó con éxito
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Inicio de sesión exitoso!");
            return "perfil";
        } catch (Exception e) {
            // Manejar la excepción de autenticación
            model.addAttribute("errorAutenticacion", e.getMessage());
            model.addAttribute("usuarioRegistro", new Usuario());
            return "principal"; // Volver a la página de inicio de sesión en caso de error
        }

    }

    @GetMapping("/perfil")
    public String mostrarPerfil(Model model) {
        // Lógica para preparar la vista de perfil
        return "perfil"; // Asumiendo que tu vista de perfil se llama "perfil.html"
    }
}

