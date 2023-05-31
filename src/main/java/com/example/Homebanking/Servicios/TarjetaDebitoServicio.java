/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Servicios;

import com.example.Homebanking.Entidades.TarjetaDebitoSubClass;
import com.example.Homebanking.Entidades.TarjetaSuperClass;
import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Repositorios.TarjetaRepositorio;
import com.example.Homebanking.Repositorios.UsuarioRepositorio;

import java.time.LocalDate;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fabi
 */
@Service
public class TarjetaDebitoServicio extends TarjetaServicio{
     
    @Autowired
    TarjetaRepositorio tarjetaRepo;
    
    @Autowired
    UsuarioRepositorio ure;

    
    public TarjetaSuperClass CrearTarjeta(String IdUsuario,Integer pin) throws Exception {
        
        Usuario usuario = ure.getById(IdUsuario);
        TarjetaSuperClass tarjeta= new TarjetaDebitoSubClass();
        tarjeta.setPin(pin);
        tarjeta.setSaldo(usuario.getCuenta().getSaldo());//saldo en la cuenta
        tarjeta.setFechaVencimiento(LocalDate.of(2028, 12, 31));
        tarjeta.setTipo("Debito");
        
      validacion2(tarjeta.getSaldo(), tarjeta.getFechaVencimiento(), tarjeta.getAlta());
        return tarjetaRepo.save(tarjeta);
}
    
     @Override
    public TarjetaSuperClass modificarTarjeta(Long IdTarjeta, String IdUsuario, Integer pin) throws Exception {
       validacion1( IdUsuario,IdTarjeta, pin);
        TarjetaSuperClass trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
        Usuario usuario = ure.getById(IdUsuario);
        
        if (trayendoTarjeta != null && usuario.getTarjetaDebito()==trayendoTarjeta) {
            
            trayendoTarjeta.setPin(pin);
            trayendoTarjeta.setSaldo(usuario.getCuenta().getSaldo());
            trayendoTarjeta.setFechaVencimiento(LocalDate.of(2035, 12, 31));
            trayendoTarjeta.setTipo("Debito");
            validacion2(trayendoTarjeta.getSaldo(), trayendoTarjeta.getFechaVencimiento(), trayendoTarjeta.getAlta());
        }
        
        return tarjetaRepo.save(trayendoTarjeta);
    }
   
    @Transactional
    public void ActualizarSaldoTarjetaDebito(String IdUsuario){
        
        Usuario usuario = ure.getById(IdUsuario);
        Double SaldoUsuario=usuario.getCuenta().getSaldo();
        TarjetaSuperClass trayendoTarjeta = usuario.getTarjetaDebito();
        do{
            trayendoTarjeta.setSaldo(SaldoUsuario);
            tarjetaRepo.save(trayendoTarjeta);
        }while(trayendoTarjeta.getSaldo()!=SaldoUsuario);
    }
    
//usa el metodo darDeBaja,eliminar y la validaciones de la clase padre.
    }

