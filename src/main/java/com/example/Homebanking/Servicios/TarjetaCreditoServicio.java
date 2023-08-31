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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fabi
 */
@Service
public class TarjetaCreditoServicio extends TarjetaServicio{
  

    @Autowired
    TarjetaRepositorio tarjetaRepo;
    
    @Autowired
    UsuarioRepositorio ure;

    @Override
    public TarjetaSuperClass CrearTarjeta(String IdUsuario,Integer pin) throws Exception {
        TarjetaSuperClass tarjetaCredito = super.CrearTarjeta(IdUsuario, pin);
        Usuario usuario = ure.getById(IdUsuario);
        
        tarjetaCredito.setSaldo(500000.00);//saldo limite
        
        tarjetaCredito.setTipo("Credito");
        
       validacion2(tarjetaCredito.getSaldo(), tarjetaCredito.getFechaVencimiento(), tarjetaCredito.getAlta());
       usuario.setTarjetaCredito(tarjetaCredito);
        ure.save(usuario);
        return tarjetaRepo.save(tarjetaCredito);
}
    
    @Transactional
    @Override
    public TarjetaSuperClass modificarTarjeta(Long IdTarjeta, String IdUsuario, Integer pinViejo,Integer pinNuevo) throws Exception {
       TarjetaSuperClass trayendoTarjeta =super.modificarTarjeta(IdTarjeta, IdUsuario, pinViejo, pinNuevo);
        validacion1( IdUsuario,IdTarjeta, pinViejo);
        
        Usuario user=ure.getById(IdUsuario);
        trayendoTarjeta.setSaldo(user.getTarjetaCredito().getSaldo());
        validacion2(trayendoTarjeta.getSaldo(), trayendoTarjeta.getFechaVencimiento(), trayendoTarjeta.getAlta());
        user.setTarjetaCredito(trayendoTarjeta);
        ure.save(user);
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
    
    //usa el metodo darDeBaja,eliminar,todos los buscar y la validaciones de la clase padre.
        
    }
    
    


   


