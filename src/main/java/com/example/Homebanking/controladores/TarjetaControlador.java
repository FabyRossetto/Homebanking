/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.controladores;

import com.example.Homebanking.Entidades.TarjetaSuperClass;

import com.example.Homebanking.Repositorios.TarjetaRepositorio;
import com.example.Homebanking.Servicios.TarjetaCreditoServicio;
import com.example.Homebanking.Servicios.TarjetaDebitoServicio;
import com.example.Homebanking.Servicios.TarjetaServicio;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/crearTarjeta")
    public String CrearTarjetas(ModelMap modelo, @RequestParam String IdUsuario, @RequestParam Integer pin) throws Exception {
        tarjetaCredito.CrearTarjeta(IdUsuario,pin);
        tarjetaDebito.CrearTarjeta(IdUsuario, pin);
        modelo.put("exito", "sus tarjetas de debito y credito se han creado con exito");
        return "sus tarjetas se han creado con exito";
    }

    @PutMapping("/ModificarTarjeta")
    public String ModificarTarjetaDebito(ModelMap modelo, @RequestParam Long IdTarjeta, @RequestParam String IdUsuario, @RequestParam Integer pinViejo,@RequestParam Integer pinNuevo) throws Exception {
        TarjetaSuperClass trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
        if (trayendoTarjeta.getTipo().equalsIgnoreCase("Credito")) {
            tarjetaCredito.modificarTarjeta(IdTarjeta, IdUsuario, pinViejo,pinNuevo);
        } else if (trayendoTarjeta.getTipo().equalsIgnoreCase("Debito")) {
            tarjetaDebito.modificarTarjeta(IdTarjeta, IdUsuario, pinViejo,pinNuevo);
        }

        return "ha modificado su tarjeta correctamente";
    }

    @PutMapping("/actualizarSaldo")
    public String ActualizarSaldo(@RequestParam String IdUsuario, @RequestParam Long IdTarjeta,@RequestParam Double MontoCompra) throws Exception {
        TarjetaSuperClass trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
        if (trayendoTarjeta.getTipo().equalsIgnoreCase("Debito")) {
            tarjetaDebito.ActualizarSaldoTarjetaDebito(IdUsuario,MontoCompra);
        }
        if (trayendoTarjeta.getTipo().equalsIgnoreCase("Credito")) {
            tarjetaCredito.modificarSaldoMaximo(MontoCompra, IdTarjeta);
        }
        
        return "La compra se ha realizado correctamente  y el saldo se ha actualizado. Su saldo actual es de "+ trayendoTarjeta.getSaldo();
    }

    @DeleteMapping("/EliminarTarjeta")
    public String EliminarTarjeta(ModelMap modelo, @RequestParam String IdUsuario,@RequestParam Long IdTarjeta) throws Exception {
try{
        tarjetaServicio.EliminarTarjeta(IdUsuario,IdTarjeta);
        modelo.put("exito", "se ha eliminado la tarjeta");
        return "se ha eliminado la tarjeta";

    }catch (Exception e) {

            return e.getMessage();
        }
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
     @GetMapping("/BuscarTarjetaPorUsuario")
    public String BuscarTarjetaPorUsuario(ModelMap modelo, @RequestParam String IdUsuario) throws Exception {

        return "Las tarjetas encontradas para ese usuario son:  " + tarjetaServicio.BuscarTarjetaPorUsuario(IdUsuario); 

    }
     @GetMapping("/BuscarTarjetaPorVto") //Este metodo recibe un String de postman y lo pasa a LocalDate para poder trabajar con el
    public List<TarjetaSuperClass> BuscarTarjetaPorVto(@RequestParam String Fecha){
          DateTimeFormatter format=new DateTimeFormatterBuilder().append(DateTimeFormatter.ofPattern("yyy-MM-dd")).toFormatter();  
        LocalDate localDate = LocalDate.parse(Fecha,format);
         
         System.out.println("fecha: " +localDate);
        return tarjetaServicio.BuscarPorFechaDeVto(localDate);

    }   
    //Busca la tarjeta a traves del Id
     @GetMapping("/BuscarTarjetaPorId")
    public String BuscarTarjetaPorId(ModelMap modelo, @RequestParam Long IdTarjeta) throws Exception {

        return "su tarjeta es " + tarjetaServicio.BuscarPorId(IdTarjeta);

    }

    

}
