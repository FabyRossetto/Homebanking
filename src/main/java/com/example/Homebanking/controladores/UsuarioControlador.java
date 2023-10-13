/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.controladores;

import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Errores.ErrorServicio;
import java.util.Map;


import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Fabi
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    com.example.Homebanking.Servicios.UsuarioServicio uSer;

    
   @PostMapping("/logincheck")
public ResponseEntity<String> loginCheck(@RequestBody Map<String, String> loginData, HttpSession session) {
    String email = loginData.get("email");
    String clave = loginData.get("clave");
    
    Usuario usuario = uSer.BucarUsuarioPorEmail(email);
    
    if (usuario != null && usuario.getClave().equals(clave)) {
         session.setAttribute("usuariosession", usuario);
        // Las credenciales son válidas
        return ResponseEntity.status(HttpStatus.OK).build();
    } else {
        // Las credenciales son inválidas
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

 
    @GetMapping("/perfil")// creo que no esta entrando en este controlador.
    @ResponseBody
public ResponseEntity<Usuario> perfilUsuario(HttpSession session) {
    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuariosession");
    if (usuarioLogueado != null) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioLogueado);
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}

    //Este metodo registra un usuario con sus datos basicos.
    @PostMapping("/crearUsuario")
    public String CrearUsuario(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String Email, @RequestParam String clave, @RequestParam String DNI) throws Exception {
        try {
            uSer.crear(nombre, apellido, Email, clave, DNI);

            modelo.put("exito", "usted se ha creado un usuario, y una cuenta en el Banco...,sus tarjetas de debito y credito le llegaran a la brevedad a su domicilio");
            return "usted se ha creado un usuario";
        } catch (Exception e) {

            return e.getMessage();
        }

    }

    //Este metodo le agrega a el usuario que le pasemos por parametro, una cuenta y tarjetas de debito y credito.
    @PostMapping("/cargarCuentayTarjetas")
    public String CargarCuentayTarjetas(ModelMap modelo, @RequestParam String Id, @RequestParam Double saldo, @RequestParam Integer clave) throws Exception {
        uSer.cargarTarjetasyCuenta(Id, saldo, clave);//es el IdUsuario
        return "se creo su cuenta y se cargaron sus tarjetas de debito y credito";
    }

    //@PathVariable= configurar variables dentro de los propios segmentos de la URL
    //HttpSession= puede almacenar los datos de la sesión en el servidor y acceder a los mismos
    @GetMapping("/modificarUsuario/{id}")
    public ResponseEntity<Usuario> modificarUsuario(@PathVariable String id, ModelMap modelo, HttpSession session) throws ErrorServicio {
        Usuario usuario = uSer.BuscarPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //Modifica todos los datos del usuario
    @PutMapping("/modificarUsuario/{Id}")
    public void ModificarUsuario(ModelMap modelo, @PathVariable String Id, @ModelAttribute Usuario usuarioForm) throws Exception {

        if (usuarioForm != null) {
            uSer.modificarDatosPersonales(Id, usuarioForm.getNombre(), usuarioForm.getApellido(), usuarioForm.getEmail(), usuarioForm.getClave(), usuarioForm.getDNI());
        } else {
            System.out.println("El controlador esta recibiendo un objeto nulo desde el Formulario");
        }
       

    }

    //En este metodo se cambia la contraseña,requiere un codigo que es enviado al email.
    @PutMapping("/modificarPass")
    public String CambiarContrasena(ModelMap modelo, @RequestParam Integer codigo, @RequestParam String claveNueva, @RequestParam String email) throws Exception {
        try {
            uSer.cambiarContraseña(codigo, claveNueva, email);

            modelo.put("exito", "la contraseña ha sido modificada con exito");
            return "usted ha modificado su contraseña con exito";
        } catch (Exception e) {

            return e.getMessage();
        }

    }

    //Da de baja el usuario
    @PatchMapping("/darDeBajaUsuario")
    public String DarDeBajaUsuario(ModelMap modelo, @RequestParam String Id) throws Exception {
        try {
            uSer.darBaja(Id);

            modelo.put("exito", "usted fue dado de baja");
            return "el usuario ha sido dado de baja";
        } catch (Exception e) {

            return e.getMessage();
        }

    }

    //Elimina al usuario con todos sus datos de la BD
    @DeleteMapping("/EliminarUsuario/{Id}")
    public void BorrarUsuario(ModelMap modelo, @PathVariable String Id) throws Exception {

        if (Id != null) {
            uSer.EliminarUsuario(Id);

        } else {
            System.out.println("El controlador esta recibiendo un objeto nulo");

        }
    }

    //busca al usuario por DNI y lo trae con todos sus datos
    @GetMapping("/BuscarUsuarioPorDNI")
    public String BuscarUsuarioDNI(ModelMap modelo, @RequestParam String DNI) {
        return "El usuario es :  " + uSer.BuscarUsuarioPorDNI(DNI);
    }

    //Busca al usuario por Apellido y lo trae con todos sus datos
    @GetMapping("/BuscarUsuarioPorApellido")
    public String BuscarUsuarioPorApellido(ModelMap modelo, @RequestParam String Apellido) {
        return "El usuario es :  " + uSer.BuscarUsuarioPorApellido(Apellido);
    }

    //Busca al usuario por email y lo trae con todos sus datos
    @GetMapping("/BuscarUsuarioPorEmail")
    public String BuscarUsuarioPorEmail(ModelMap modelo, @RequestParam String email) {
        return "El usuario es :  " + uSer.BucarUsuarioPorEmail(email);
    }

    //Busca al usuario por la cuenta que le sea pasada por parametro
    @GetMapping("/BuscarUsuarioPorCuenta")
    public String BuscarUsuarioPorCuenta(ModelMap modelo, @RequestParam Long IdCuenta) {
        return "El usuario es :  " + uSer.BuscarPorCuenta(IdCuenta);
    }
}
