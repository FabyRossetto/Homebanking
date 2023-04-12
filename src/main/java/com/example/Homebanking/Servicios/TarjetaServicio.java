/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Servicios;

import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Entidades.TarjetaSuperClass;
import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Repositorios.TarjetaRepositorio;
import com.example.Homebanking.Repositorios.UsuarioRepo;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Tarjeta (Fabi) 
 * crearTarjeta 
 * eliminarTarjeta 
 * darBaja 
 * actualizarSaldo
 * validarTarjeta (límite de tarjeta disponible, usuario, no esté vencida)
 *
 * @author Fabi
 */
@Service
public class TarjetaServicio {

    @Autowired
    TarjetaSuperClass tarjeta;

    @Autowired
    TarjetaRepositorio tarjetaRepo;
    
    @Autowired
    UsuarioRepo ure;

    @Transactional
    public TarjetaSuperClass CrearTarjeta(String IdUsuario,Long IdTarjeta,Integer pin) throws Exception {//repensar el parametro idTarjeta,creo q no es necesario

          Usuario usuario = ure.getById(IdUsuario);
        if(usuario.getTarjeta()==null){
        tarjeta.setUsuario(usuario);
        tarjeta.setId(IdTarjeta);
        tarjeta.setPin(pin);
        tarjeta.setFechaVencimiento(LocalDate.of(2028, 12, 31));//averiguar como le agrego una fecha,de aca a 3 años , por ejemplo
        
        }
        return tarjetaRepo.save(tarjeta);
    }
    

//    @Transactional
//    public Tarjeta CrearTarjetaDebito(Long IdTarjeta, Usuario usuario, Integer pin) throws Exception {
//        validaciones(IdTarjeta,usuario,pin,tarjeta.getSaldoDebito(),tarjeta.getFechaVencimiento(),tarjeta.getAlta());
//        tarjeta.setId(IdTarjeta);
//        tarjeta.setUsuario(usuario);
//        tarjeta.setPin(pin);
//        tarjeta.setSaldoDebito(usuario.getCuenta().getSaldo());
//        tarjeta.setFechaVencimiento(new Date());//averiguar como le agrego una fecha,de aca a 3 años , por ejemplo
//        
//        Tarjeta tarjetaguardada = tarjetaRepo.save(tarjeta);
//        return tarjetaguardada;
//    }
//
//    @Transactional
//    public Tarjeta modificarTarjetaDebito(Long IdTarjeta, Usuario usuario, Integer pin) throws Exception {
//        validaciones(IdTarjeta,usuario,pin,tarjeta.getSaldoDebito(),tarjeta.getFechaVencimiento(),tarjeta.getAlta());
//        Tarjeta trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
//        
//        if (trayendoTarjeta != null) {
//            trayendoTarjeta.setUsuario(usuario);
//            trayendoTarjeta.setPin(pin);
//            trayendoTarjeta.setSaldoDebito(usuario.getCuenta().getSaldo());
//            trayendoTarjeta.setFechaVencimiento(new Date());//averiguar como le agrego una fecha,de aca a 3 años , por ejemplo
//            
//
//        }
//        return tarjetaRepo.save(trayendoTarjeta);
//    }
//    
//    @Transactional
//    public void ActualizarSaldoTarjetaDebito(Long IdTarjeta){
//        Tarjeta trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
//        Double SaldoUsuario=trayendoTarjeta.getUsuario().getCuenta().getSaldo();
//        do{
//            trayendoTarjeta.setSaldoDebito(SaldoUsuario);
//            tarjetaRepo.save(trayendoTarjeta);
//        }while(trayendoTarjeta.getSaldoDebito()!=SaldoUsuario);
//    }
//
//    public void EliminarTarjeta(Long IdTarjeta) {
//        Tarjeta trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
//        if (trayendoTarjeta != null) {
//            tarjetaRepo.delete(trayendoTarjeta);
//        }
//    }
//   @Transactional
//    public void DarDeBajaTarjeta(Long IdTarjeta) {
//        TarjetaSuperClass trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
//        if (trayendoTarjeta != null) {
//            trayendoTarjeta.setAlta(false);
//        }
//
//    }
     public void validacion1(String IdUsuario,Long Id,Integer pin) throws Exception {
if (IdUsuario == null) {
            throw new Exception(" El usuario no puede ser nulo");
        }
        if (Id == null || Id.toString().trim().isEmpty()) {
            throw new Exception(" El Id no puede ser nulo");
        }
        
        
         if (pin == null || pin.toString().trim().isEmpty() || pin.toString().length() > 4) {
            throw new Exception(" El pin es nulo o no lo esta ingresando bien");
        }
        

    }
    public void validacion2(Double Saldo,LocalDate fechaDeVencimiento,Boolean Alta)throws Exception{
        if (Saldo<0||Saldo==null) {
          throw new Exception(" Su cuenta esta vacia");
        }
        LocalDate fechaActual= LocalDate.now();//ver si se crea con la fecha actual
         if (fechaActual.isAfter(fechaDeVencimiento)) {
            System.out.println("La fecha de vencimiento ha pasado");
         }
        if (Alta == null || Alta==false) {
            throw new Exception(" La tarjeta esta dada de baja");
        }
    }

}
