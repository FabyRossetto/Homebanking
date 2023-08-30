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
import java.util.Objects;

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
   
    
    @Override
    public TarjetaSuperClass CrearTarjeta(String IdUsuario,Integer pin) throws Exception {
        TarjetaSuperClass tarjetaDebito = super.CrearTarjeta(IdUsuario, pin);
        Usuario usuario = ure.getById(IdUsuario);
        
        tarjetaDebito.setSaldo(usuario.getCuenta().getSaldo());//saldo en la cuenta
        tarjetaDebito.setTipo("Debito");
        
      validacion2(tarjetaDebito.getSaldo(), tarjetaDebito.getFechaVencimiento(), tarjetaDebito.getAlta());
      usuario.setTarjetaDebito(tarjetaDebito);
      ure.save(usuario);
      
        return tarjetaRepo.save(tarjetaDebito);
}
    
     @Override
    public TarjetaSuperClass modificarTarjeta(Long IdTarjeta, String IdUsuario, Integer pinViejo,Integer pinNuevo) throws Exception {
        TarjetaSuperClass trayendoTarjeta =super.modificarTarjeta(IdTarjeta, IdUsuario, pinViejo, pinNuevo);
        validacion1( IdUsuario,IdTarjeta, pinViejo);
        
        Usuario user=ure.getById(IdUsuario);
        trayendoTarjeta.setSaldo(user.getCuenta().getSaldo());
        validacion2(trayendoTarjeta.getSaldo(), trayendoTarjeta.getFechaVencimiento(), trayendoTarjeta.getAlta());
        user.setTarjetaCredito(trayendoTarjeta);
        ure.save(user);
        return tarjetaRepo.save(trayendoTarjeta); 
    }
            
    @Transactional
    public void ActualizarSaldoTarjetaDebito(String IdUsuario,Double MontoCompra) throws Exception{
        
        Usuario usuario = ure.getById(IdUsuario);
        Double SaldoUsuario=usuario.getCuenta().getSaldo();
        TarjetaSuperClass trayendoTarjeta = usuario.getTarjetaDebito();
        if (trayendoTarjeta!=null) {
            
            if(MontoCompra>SaldoUsuario){
                throw new Exception(" Fondos insuficientes");
            }
            validacion2(trayendoTarjeta.getSaldo(), trayendoTarjeta.getFechaVencimiento(), trayendoTarjeta.getAlta());
            trayendoTarjeta.setSaldo(SaldoUsuario-MontoCompra);
            tarjetaRepo.save(trayendoTarjeta);
       
    }
    
//usa el metodo darDeBaja,eliminar,todos los buscar y la validaciones de la clase padre.
    }
}

