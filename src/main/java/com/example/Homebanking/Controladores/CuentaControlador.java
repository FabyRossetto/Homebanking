package com.example.Homebanking.Controladores;

import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Repositorios.CuentaRepositorio;
import com.example.Homebanking.Repositorios.TarjetaRepositorio;
import com.example.Homebanking.Repositorios.UsuarioRepositorio;
import com.example.Homebanking.Servicios.CuentaServicio;

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
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private TarjetaRepositorio tarjetaRepositorio;

//    @Autowired
//    private TransferenciaRepositorio transferenciaRepositorio;
//    
    @Autowired
    private CuentaServicio cuentaservicio;



    @GetMapping("/guardarCuenta")
    public String guardarCuenta(ModelMap vista) {

        return "administracionCuenta"; //me devuelve la vista
    }
    
     @PostMapping("/guardarCuenta")
    public String guardar(@RequestParam Long IdCuenta,Double saldo, ModelMap modelo) throws Exception {
        try {
            cuentaservicio.guardar(IdCuenta, saldo);
            modelo.put("Gracias por elegirnos", ("Cuenta dada de alta con éxito"));
        } catch (Exception e) {
            e.getMessage();
            modelo.put("Error", "La cuenta no ha podido ser dada de alta");
        }
        return "administracionCuenta";
    }
    
    
    @DeleteMapping("/eliminarCuenta")
    public String eliminarCenta(@RequestParam Long IdCuenta) throws Exception{
        cuentaservicio.borrarPorId(IdCuenta);
       
        return "la cuenta fue eliminada";
    }

}
//alta?