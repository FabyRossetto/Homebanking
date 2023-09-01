package com.example.Homebanking.Controladores;

import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Repositorios.CuentaRepositorio;
import com.example.Homebanking.Repositorios.TarjetaRepositorio;
import com.example.Homebanking.Repositorios.UsuarioRepositorio;
import com.example.Homebanking.Servicios.CuentaServicio;
import com.example.Homebanking.Servicios.TarjetaServicio;
import com.example.Homebanking.Servicios.UsuarioServicio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/administracionCuenta")
public class CuentaControlador {

    @Autowired
    private CuentaRepositorio cuentaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @Autowired
    private TarjetaRepositorio tarjetaRepositorio;

//    @Autowired
//    private TransferenciaRepositorio transferenciaRepositorio;
//    
    @Autowired
    private CuentaServicio cuentaservicio;

//    @Autowired
//    private UsuarioServicio usuarioservicio;
//    @Autowired
//    TarjetaServicio tarjetaservicio;
    @GetMapping("/guardarCuenta")
    public String guardarCuenta(ModelMap vista) {

        return "administracionCuenta"; //me devuelve la vista
    }

    @PostMapping("/guardarCuenta")
    public String guardarCuenta(@RequestParam String idUser, @RequestParam Double saldo, ModelMap modelo) throws Exception {
        try {
            cuentaservicio.guardar(idUser, saldo);
            modelo.put("Gracias por elegirnos", ("Cuenta dada de alta con éxito"));
        } catch (Exception e) {
            e.getMessage();
            modelo.put("Error", "La cuenta no ha podido ser dada de alta");
        }
        return "administracionCuenta";
    }
    
        
    

    @PostMapping("/ingresarDinero")
    public String ingresarDinero(@RequestParam Double saldoActual, @RequestParam Double saldo, @RequestParam Long Id, @RequestParam Double deposito, ModelMap modelo) throws Exception {
        Cuenta cuenta = cuentaRepositorio.getById(Id);
        try {
            cuentaservicio.ingresarDinero(saldoActual, saldo, deposito, Id);
            modelo.put("Operación realizada con éxito", ("Gracias por utilizar nuestros servicios"));
        } catch (Exception e) {
            e.getMessage();
            modelo.put("Error", "Intente nuevamente");
        }
        return "administracionCuenta"; //o lo redirigo al html de ingresar dinero?
    }


    @PostMapping("/retirarDinero")
    public String retirarDinero(@RequestParam Double saldoActual, @RequestParam Double saldo, @RequestParam Double extraccion, @RequestParam Long Id, ModelMap modelo) throws Exception {
        Cuenta cuenta = cuentaRepositorio.getById(Id);

        try {
            cuentaservicio.ingresarDinero(saldoActual, saldo, extraccion, Id);
            modelo.put("Operación realizada con éxito", ("Gracias por utilizar nuestros servicios"));
        } catch (Exception e) {
            e.getMessage();
            modelo.put("Error", "Intente nuevamente");
        }

        return "administracionCuenta";

    }

    @DeleteMapping("/eliminarCuenta")
    public String eliminarCuenta(@RequestParam Long Id, ModelMap modelo) throws Exception {
        try {
             cuentaservicio.borrarPorId(Id);
              modelo.put("Gracias por utilizar nuestros servicios", "Cuenta eliminada");
        } catch (Exception e) {
            modelo.put("Error","No se puede eliminar la cuenta");
        }
       
        return "administracionCuenta";

   
         }
     }


//}
//
//
//
////alta?
