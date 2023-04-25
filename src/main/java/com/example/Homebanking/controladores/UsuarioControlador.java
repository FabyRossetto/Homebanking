/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.controladores;


import com.example.Homebanking.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Fabi
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio uSer;

    @PostMapping("/crearUsuario")
    public String CrearUsuario(ModelMap modelo, @RequestParam String Id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String Email, @RequestParam Integer clave) throws Exception {
        try {
            uSer.crear(Id, nombre, apellido, Email, clave);
            
            modelo.put("exito", "usted se ha creado un usuario, y una cuenta en el Banco...,sus tarjetas de debito y credito le llegaran a la brevedad a su domicilio");
            return "usted se ha creado un usuario";
        } catch (Exception e) {

            return e.getMessage();
        }

    }
    @PostMapping("/cargarCuentayTarjetas")
    public String CargarCuentayTarjetas(ModelMap modelo, @RequestParam String Id,@RequestParam Double saldo, @RequestParam Integer clave) throws Exception{
        uSer.cargarTarjetasyCuenta(Id,saldo, clave);
        return "se creo su cuenta y se cargaron sus tarjetas de debito y credito";
    }
}
