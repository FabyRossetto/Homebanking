
package com.example.Homebanking.controladores;
import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Repositorios.UsuarioRepositorio;
import com.example.Homebanking.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

/**
 *
 * @author Fabi
 */
@RestController
@CrossOrigin(origins = "null")
@RequestMapping(value = "/administracionUsuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio uSer;
    @Autowired
    private UsuarioRepositorio usuarioRepo;



    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@ModelAttribute Usuario usuario) {
        System.out.println("Recibida solicitud de registro.");

        boolean registroExitoso = intentarRegistro(usuario);

        if (registroExitoso) {
            // Registro exitoso, devolver un ResponseEntity con estado HTTP 200 (OK)
            return new ResponseEntity<>("Registro exitoso", HttpStatus.OK);
        } else {
            // Si el registro falla, puedes devolver un ResponseEntity con un estado HTTP 400 (Bad Request) u otro adecuado
            return new ResponseEntity<>("Error en el registro", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean intentarRegistro(Usuario usuario) {
        try {
            uSer.crear(usuario.getNombre(), usuario.getApellido(), usuario.getDNI(), usuario.getEmail(), usuario.getClave());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

//public class LoginControlador {
//    @Autowired
//    private UsuarioServicio usuarioServicio; // Debes inyectar el servicio adecuado
//
//    @PostMapping("/login")
//    public ResponseEntity<String> iniciarSesion(@RequestParam String nombreDeUsuario, @RequestParam String contrasena) {
//        System.out.println("Recibida solicitud de inicio de sesión.");
//
//        boolean inicioSesionExitoso = intentarInicioSesion(nombreDeUsuario, contrasena);
//
//        if (inicioSesionExitoso) {
//            // Inicio de sesión exitoso, devolver un ResponseEntity con estado HTTP 200 (OK)
//            return new ResponseEntity<>("Inicio de sesión exitoso", HttpStatus.OK);
//        } else {
//            // Si el inicio de sesión falla, puedes devolver un ResponseEntity con un estado HTTP 401 (Unauthorized) u otro adecuado
//            return new ResponseEntity<>("Inicio de sesión fallido", HttpStatus.UNAUTHORIZED);
//        }
//    }
//
//    private boolean intentarInicioSesion(String nombreDeUsuario, String contrasena) {
//        try {
//            // Aquí debes implementar la lógica para verificar las credenciales del usuario
//            // Puedes utilizar el servicio de usuario para verificar el inicio de sesión
//            // Si el inicio de sesión es exitoso, devuelve true, de lo contrario, devuelve false.
//            return usuarioServicio.verificarCredenciales(nombreDeUsuario, contrasena);
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}
//
//mensaje / status/ no string/response entity
//cambie el form a usuario en el script/
//Este metodo le agrega a el usuario que le pasemos por parametro, una cuenta y tarjetas de debito y credito.
//    @PostMapping("/cargarCuentayTarjetas")
//    public String CargarCuentayTarjetas(ModelMap modelo,
//            @RequestParam String Id,
//            @RequestParam Double saldo,
//            @RequestParam Integer clave) throws Exception {
//        uSer.cargarTarjetasyCuenta(Id, saldo, clave);//es el IdUsuario
//        return "se creo su cuenta y se cargaron sus tarjetas de debito y credito";
//    }
//
//    //Modifica todos los datos del usuario
//    @PutMapping("/modificarUsuario")
//    public String ModificarUsuario(ModelMap modelo,
//            @RequestParam String Id,
//            @RequestParam String nombre,
//            @RequestParam String apellido,
//            @RequestParam String Email,
//            @RequestParam String clave,
//            @RequestParam String DNI) throws Exception {
//        try {
//            uSer.modificarDatosPersonales(Id, nombre, apellido, Email, clave, DNI);
//
//            modelo.put("exito", "usted ha modificado sus datos con exito");
//            return "usted ha modificado sus datos con exito";
//        } catch (Exception e) {
//
//            return e.getMessage();
//        }
//
//    }
//
//    //En este metodo se cambia la contraseña,requiere un codigo que es enviado al email.
//    @PutMapping("/modificarPass")
//    public String CambiarContrasena(ModelMap modelo,
//            @RequestParam Integer codigo,
//            @RequestParam String claveNueva,
//            @RequestParam String email) throws Exception {
//        try {
//            uSer.cambiarContraseña(codigo, claveNueva, email);
//
//            modelo.put("exito", "la contraseña ha sido modificada con exito");
//            return "usted ha modificado su contraseña con exito";
//        } catch (Exception e) {
//
//            return e.getMessage();
//        }
//
//    }
//
//    //Da de baja el usuario
//    @PatchMapping("/darDeBajaUsuario")
//    public String DarDeBajaUsuario(ModelMap modelo,
//            @RequestParam String Id) throws Exception {
//        try {
//            uSer.darBaja(Id);
//
//            modelo.put("exito", "usted fue dado de baja");
//            return "el usuario ha sido dado de baja";
//        } catch (Exception e) {
//
//            return e.getMessage();
//        }
//
//    }
//
//    //Elimina al usuario con todos sus datos de la BD
//    @DeleteMapping("/EliminarUsuario")
//    public String BorrarUsuario(ModelMap modelo,
//            @RequestParam String Id) throws Exception {
//        try {
//            uSer.EliminarUsuario(Id);
//
//            modelo.put("exito", "el usuario fue eliminado");
//
//            return "usted fue eliminado de la base de datos";
//
//        } catch (Exception e) {
//
//            return e.getMessage();
//        }
//    }
//
//    //busca al usuario por DNI y lo trae con todos sus datos
//    @GetMapping("/BuscarUsuarioPorDNI")
//    public String BuscarUsuarioDNI(ModelMap modelo,
//            @RequestParam String DNI
//    ) {
//        return "El usuario es :  " + uSer.BuscarUsuarioPorDNI(DNI);
//    }
//
//    //Busca al usuario por Apellido y lo trae con todos sus datos
//    @GetMapping("/BuscarUsuarioPorApellido")
//    public String BuscarUsuarioPorApellido(ModelMap modelo,
//            @RequestParam String Apellido
//    ) {
//        return "El usuario es :  " + uSer.BuscarUsuarioPorApellido(Apellido);
//    }
//
//    //Busca al usuario por email y lo trae con todos sus datos
//    @GetMapping("/BuscarUsuarioPorEmail")
//    public String BuscarUsuarioPorEmail(ModelMap modelo,
//            @RequestParam String email
//    ) {
//        return "El usuario es :  " + uSer.BucarUsuarioPorEmail(email);
//    }
//
//    //Busca al usuario por la cuenta que le sea pasada por parametro
//    @GetMapping("/BuscarUsuarioPorCuenta")
//    public String BuscarUsuarioPorCuenta(ModelMap modelo,
//            @RequestParam Long IdCuenta
//    ) {
//        return "El usuario es :  " + uSer.BuscarPorCuenta(IdCuenta);
//    }
//}
