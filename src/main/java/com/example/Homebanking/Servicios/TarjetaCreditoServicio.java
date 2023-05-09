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
public class TarjetaCreditoServicio extends TarjetaServicio{
    @Autowired
    TarjetaSuperClass tarjetaCredito;

    @Autowired
    TarjetaRepositorio tarjetaRepo;
    
    @Autowired
    UsuarioRepositorio ure;

    @Override
    public TarjetaSuperClass CrearTarjeta(String IdUsuario,Integer pin) throws Exception {
        
          Usuario usuario = ure.getById(IdUsuario);
        if(usuario.getTarjetaCredito()==null){
        tarjeta.setUsuario(usuario);
       
        tarjeta.setPin(pin);
        tarjeta.setSaldo(500000.00);//saldo limite
        tarjeta.setFechaVencimiento(LocalDate.of(2028, 12, 31));
        tarjeta.setTipo("Credito");
         
        }
//        validacion2(tarjeta.getSaldo(), tarjeta.getFechaVencimiento(), tarjeta.getAlta());//ver si esta validacion la necesito aca o en un metodo para gastar
        return tarjetaRepo.save(tarjeta);
}
    @Transactional
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
            trayendoTarjeta.setTipo("Credito");
            
        }
        validacion2(trayendoTarjeta.getSaldo(), trayendoTarjeta.getFechaVencimiento(), trayendoTarjeta.getAlta());
        return tarjetaRepo.save(trayendoTarjeta); 
    }
    
    
    @Transactional
    public void modificarSaldoMaximo( Double MontoCompra, Long IdTarjeta) throws Exception {
        TarjetaSuperClass trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
        
        if (trayendoTarjeta!=null) {
            
            if(MontoCompra>trayendoTarjeta.getSaldo()){
                throw new Exception(" Fondos insuficientes");
            }
            
            validacion2(trayendoTarjeta.getSaldo(), trayendoTarjeta.getFechaVencimiento(), trayendoTarjeta.getAlta());
            
            trayendoTarjeta.setSaldo(trayendoTarjeta.getSaldo()- MontoCompra);
            
           tarjetaRepo.save(trayendoTarjeta);

}
        }
    //hacer metodo de eliminar en ambas tarjetas!
        
    }
    
    


   


