/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Servicios;

import com.example.Homebanking.Entidades.TarjetaSuperClass;
import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Repositorios.TarjetaRepositorio;
import com.example.Homebanking.Repositorios.UsuarioRepo;
import java.util.Date;
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
    TarjetaSuperClass tarjeta;

    @Autowired
    TarjetaRepositorio tarjetaRepo;
    
    @Autowired
    UsuarioRepo ure;

    @Override
    public TarjetaSuperClass CrearTarjeta(String IdUsuario,Long IdTarjeta,Integer pin) throws Exception {
        validacion1( IdUsuario,IdTarjeta, pin);
          Usuario usuario = ure.getById(IdUsuario);
        if(usuario.getTarjeta()==null){
        tarjeta.setUsuario(usuario);
        tarjeta.setId(IdTarjeta);
        tarjeta.setPin(pin);
        tarjeta.setSaldo(500000.00);//saldo limite
        tarjeta.setFechaVencimiento(new Date());//averiguar como le agrego una fecha,de aca a 3 años , por ejemplo
         
        }
//        validacion2(tarjeta.getSaldo(), tarjeta.getFechaVencimiento(), tarjeta.getAlta());//ver si esta validacion la necesito aca o en un metodo para gastar
        return tarjetaRepo.save(tarjeta);
}


   
}

