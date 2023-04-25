/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.controladores;

import com.example.Homebanking.Entidades.TarjetaSuperClass;
import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Repositorios.TarjetaRepositorio;
import com.example.Homebanking.Servicios.TarjetaCreditoServicio;
import com.example.Homebanking.Servicios.TarjetaDebitoServicio;
import com.example.Homebanking.Servicios.TarjetaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    TarjetaCreditoServicio tarjetaCredito;

    @Autowired
    TarjetaDebitoServicio tarjetaDebito;
    
    @Autowired
    @Qualifier("tarjetaServicio")//esta anotacion es para indicarle que clase quiero que utilice,porque al ser la clase padre,el programa no sabia cual de las 3 usar
    TarjetaServicio tarjetaServicio;

    @Autowired
    TarjetaRepositorio tarjetaRepo;

    @PostMapping("/crearTarjetaCredito")
    public String CrearTarjetas(ModelMap modelo, @RequestParam String IdUsuario, @RequestParam Integer pin) throws Exception {
        tarjetaCredito.CrearTarjeta(IdUsuario, pin);
        tarjetaDebito.CrearTarjeta(IdUsuario, pin);
        modelo.put("exito", "sus tarjetas de debito y credito se han creado con exito");
        return "sus tarjetas se han creado con exito";
    }

    @PutMapping("/ModificarTarjeta")
    public String ModificarTarjetaDebito(ModelMap modelo, @RequestParam Long IdTarjeta, @RequestParam String IdUsuario, @RequestParam Integer pin) throws Exception {
        TarjetaSuperClass trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
        if (trayendoTarjeta.getTipo().equalsIgnoreCase("Credito")) {
            tarjetaCredito.modificarTarjeta(IdTarjeta, IdUsuario, pin);
        } else if (trayendoTarjeta.getTipo().equalsIgnoreCase("Debito")) {
            tarjetaDebito.modificarTarjeta(IdTarjeta, IdUsuario, pin);
        }

        return "ha modificado su tarjeta correctamente";
    }

    @PutMapping("/actualizarSaldo")
    public String ActualizarSaldo(ModelMap modelo, @RequestParam Long IdTarjeta,@RequestParam Double MontoCompra) throws Exception {
        TarjetaSuperClass trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
        if (trayendoTarjeta.getTipo().equalsIgnoreCase("Debito")) {
            tarjetaDebito.ActualizarSaldoTarjetaDebito(IdTarjeta);
        }
        if (trayendoTarjeta.getTipo().equalsIgnoreCase("Credito")) {
            tarjetaCredito.modificarSaldoMaximo(MontoCompra, IdTarjeta);
        }
        modelo.put("exito", "La compra se ha realizado correctamente y el saldo se ha actualizado");
        return "La compra se ha realizado correctamente  y el saldo se ha actualizado. Su saldo actual es de "+ trayendoTarjeta.getSaldo();
    }

    @DeleteMapping("/EliminarTarjeta")
    public String EliminarTarjeta(ModelMap modelo, @RequestParam Long IdTarjeta) throws Exception {

        tarjetaServicio.EliminarTarjeta(IdTarjeta);
        modelo.put("exito", "se ha eliminado la tarjeta");
        return "se ha eliminado la tarjeta";

    }

    @PutMapping("/darDeBaja")
    public String DarDeBaja(ModelMap modelo, @RequestParam Long IdTarjeta) throws Exception {
        try {
            tarjetaServicio.DarDeBajaTarjeta(IdTarjeta);
            modelo.put("exito", "su tarjeta se ha dado de baja");
            return "su tarjeta se ha dado de baja";
        } catch (Exception exception) {
            throw new Exception(" su tarjeta no existe o ya ha sido dada de baja");
        }

    }

}
