/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.controladores;

import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Servicios.TarjetaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Fabi
 */
@RestController
@RequestMapping("/tarjeta")
public class TarjetaControlador {
    
    @Autowired
    TarjetaServicio tarjetaServ;
    
     @PostMapping("/crearTarjetaCredito")
     public String CrearTarjetaCredito (ModelMap modelo, @RequestParam String IdUsuario,@RequestParam Long IdTarjeta, @RequestParam Integer pin) throws Exception {
        
        tarjetaServ.CrearTarjetaCredito(IdUsuario,IdTarjeta,  pin);
        modelo.put("exito", "su tarjeta se ha creado con exito");
        return "su tarjeta se ha creado con exito";
    }
     
//      @PostMapping("/crearTarjetaDebito")
//     public String CrearTarjetaDebito (ModelMap modelo,@RequestParam Long IdTarjeta, @RequestParam Usuario usuario, @RequestParam Integer pin) throws Exception {
//        
//        tarjetaServ.CrearTarjetaDebito(IdTarjeta, usuario, pin);
//        modelo.put("exito", "su tarjeta se ha creado con exito");
//        return "su tarjeta se ha creado con exito";
//    }
//     
//     @PutMapping("/ModificarTarjetaDebito")
//     public String ModificarTarjetaDebito (ModelMap modelo,@RequestParam Long IdTarjeta, @RequestParam Usuario usuario, @RequestParam Integer pin) throws Exception {
//        
//        tarjetaServ.modificarTarjetaDebito(IdTarjeta, usuario, pin);
//        modelo.put("exito", "ha modificado su tarjeta de debito correctamente");
//        return "ha modificado su tarjeta de debito correctamente";
//    }
//     
//     @PutMapping("/actualizarSaldo")
//     public String ActualizarSaldoTarjetaDebito (ModelMap modelo,@RequestParam Long IdTarjeta) throws Exception {
//        
//        tarjetaServ.ActualizarSaldoTarjetaDebito(IdTarjeta);
//        modelo.put("exito", "el saldo se ha actualizado");
//        return "el saldo se ha actualizado";
//    }
//     
//     @DeleteMapping("/EliminarTarjeta")
//     public String EliminarTarjeta (ModelMap modelo,@RequestParam Long IdTarjeta) throws Exception {
//        
//        tarjetaServ.EliminarTarjeta(IdTarjeta);
//        modelo.put("exito", "se ha eliminado la tarjeta");
//        return "se ha eliminado la tarjeta";
//     }
//     
     @PutMapping("/darDeBaja")
     public String DarDeBaja (ModelMap modelo,@RequestParam Long IdTarjeta) throws Exception {
        
        tarjetaServ.DarDeBajaTarjeta(IdTarjeta);
        modelo.put("exito", "su tarjeta se ha dado de baja");
        return "su tarjeta se ha dado de baja";
    }
     
    
}
