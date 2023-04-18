/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Servicios;

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
    TarjetaSuperClass tarjetaDebito;

    @Autowired
    TarjetaRepositorio tarjetaRepo;
    
    @Autowired
    UsuarioRepositorio ure;

    @Override
    public TarjetaSuperClass CrearTarjeta(String IdUsuario,Long IdTarjeta,Integer pin) throws Exception {
        validacion1( IdUsuario,IdTarjeta, pin);
          Usuario usuario = ure.getById(IdUsuario);
        if(usuario.getTarjetaDebito()==null){
        tarjeta.setUsuario(usuario);
        tarjeta.setId(IdTarjeta);
        tarjeta.setPin(pin);
        tarjeta.setSaldo(usuario.getCuenta().getSaldo());//saldo en la cuenta
        tarjeta.setFechaVencimiento(LocalDate.of(2028, 12, 31));//averiguar como le agrego una fecha,de aca a 3 años , por ejemplo
        tarjeta.setTipo("Debito");
        }
        validacion2(tarjeta.getSaldo(), tarjeta.getFechaVencimiento(), tarjeta.getAlta());
        return tarjetaRepo.save(tarjeta);
}
     @Override
    public TarjetaSuperClass modificarTarjeta(Long IdTarjeta, String IdUsuario, Integer pin) throws Exception {
       validacion1( IdUsuario,IdTarjeta, pin);
        TarjetaSuperClass trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
        Usuario usuario = ure.getById(IdUsuario);
        
        if (trayendoTarjeta != null) {
            trayendoTarjeta.setUsuario(usuario);
            trayendoTarjeta.setPin(pin);
            trayendoTarjeta.setSaldo(usuario.getCuenta().getSaldo());
            trayendoTarjeta.setFechaVencimiento(LocalDate.of(2035, 12, 31));
            trayendoTarjeta.setTipo("Debito");
            
        }
        validacion2(trayendoTarjeta.getSaldo(), trayendoTarjeta.getFechaVencimiento(), trayendoTarjeta.getAlta());
        return tarjetaRepo.save(trayendoTarjeta);
    }
   
    @Transactional
    public void ActualizarSaldoTarjetaDebito(Long IdTarjeta){
        TarjetaSuperClass trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
        Double SaldoUsuario=trayendoTarjeta.getUsuario().getCuenta().getSaldo();
        do{
            trayendoTarjeta.setSaldo(SaldoUsuario);
            tarjetaRepo.save(trayendoTarjeta);
        }while(trayendoTarjeta.getSaldo()!=SaldoUsuario);
    }
    

    }

