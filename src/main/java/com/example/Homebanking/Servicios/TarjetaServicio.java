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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Cuando hagamos la autorizacion de usuarios, es importante que a algunos
 * metodos solo puedan acceder los administradores: como modificar,eliminar o
 * dar de baja.
 *
 */
@Service
public class TarjetaServicio {

    @Autowired
    TarjetaSuperClass tarjeta1;

    @Autowired
    TarjetaRepositorio tarjetaRepo;

    @Autowired
    UsuarioRepositorio ure;

    //Este metodo es llamado por sus clases hijas para crear las tarjetas.
    //Es sobreescrito solo para cambiar el saldo y el tipo,ya que en eso se diferencian la tarjeta de debito y la de credito.
    @Transactional
    public TarjetaSuperClass CrearTarjeta(String IdUsuario, Integer pin) throws Exception {

        TarjetaSuperClass tarjeta = new TarjetaSuperClass();
        tarjeta.setPin(pin);
        tarjeta.setFechaVencimiento(LocalDate.of(2028, 12, 31));
        tarjetaRepo.save(tarjeta);
        return tarjeta;
    }

    //Este metodo es llamado por sus clases hijas para modificar las tarjetas.
    //Es sobreescrito solo por el saldo,ya que en eso se diferencian ambas tarjetas.
    //Solo se va a poder modificar el pin y la fecha de vto,por cuestiones de seguridad.
    //El saldo se setea con lo que le quede al usuario en su cuenta,para la de debito. Y con lo que le quede de saldo para la de credito
    @Transactional
    public TarjetaSuperClass modificarTarjeta(Long IdTarjeta, String IdUsuario, Integer pinViejo, Integer pinNuevo) throws Exception {

        TarjetaSuperClass trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
        Usuario usuario = ure.getById(IdUsuario);

        if (trayendoTarjeta != null && (usuario.getTarjetaCredito() == trayendoTarjeta || usuario.getTarjetaDebito() == trayendoTarjeta)) {

            trayendoTarjeta.setPin(pinNuevo);

            trayendoTarjeta.setFechaVencimiento(LocalDate.of(2035, 12, 31));
            trayendoTarjeta.setTipo(trayendoTarjeta.getTipo());
            if (trayendoTarjeta.getTipo().equalsIgnoreCase("Debito")) {
                usuario.setTarjetaDebito(trayendoTarjeta);
            }
            if (trayendoTarjeta.getTipo().equalsIgnoreCase("Credito")) {
                usuario.setTarjetaCredito(trayendoTarjeta);
            }
            ure.save(usuario);
        }

        return tarjetaRepo.save(trayendoTarjeta);
    }

    //Este metodo setea a null las tarjetas de debito y credito del usuario,para luego eliminarlas
    @Transactional
    public void EliminarTarjeta(String IdUsuario,Long IdTarjeta) {
       Usuario user= ure.getById(IdUsuario);
       TarjetaSuperClass tarjeta= tarjetaRepo.buscarPorId(IdTarjeta);
       if(tarjeta.getTipo().equalsIgnoreCase("Credito")){
       user.setTarjetaCredito(null);
       }
        if(tarjeta.getTipo().equalsIgnoreCase("Debito")){
       user.setTarjetaDebito(null);
       }
       ure.save(user);
       tarjetaRepo.delete(tarjeta);
        

    }

    //Este metodo da de baja una tarjeta,sea de credito o debito
    @Transactional
    public void DarDeBajaTarjeta(Long IdTarjeta) {

        TarjetaSuperClass trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
        if (trayendoTarjeta != null || trayendoTarjeta.getAlta() == true) {
            trayendoTarjeta.setAlta(false);

        }

    }

    //Busca las tarjetas del usuario pasado por parametro.
    public List<TarjetaSuperClass> BuscarTarjetaPorUsuario(String IdUsuario) {
        Usuario usuario = ure.getById(IdUsuario);
        List<TarjetaSuperClass> tarjeta = new ArrayList();

        tarjeta.add(usuario.getTarjetaCredito());
        tarjeta.add(usuario.getTarjetaDebito());

        return tarjeta;
    }

    //trae la lista de tarjetas que tengan como fecha de vto la que le introduimosc por parametro
    public List<TarjetaSuperClass> BuscarPorFechaDeVto(LocalDate fechaVto) {

        return tarjetaRepo.buscarPorVto(fechaVto);
    }

    //trae la tarjeta que tenga el id que le pasamos
    public TarjetaSuperClass BuscarPorId(Long IdTarjeta) {
        TarjetaSuperClass tarjetaEncontrada = tarjetaRepo.buscarPorId(IdTarjeta);
        return tarjetaEncontrada;
    }

    //hacer un metodo que muestre los ultimos movimientos!
    public void validacion1(String IdUsuario, Long IdTarjeta, Integer pin) throws Exception {
        if (IdUsuario == null) {
            throw new Exception(" El usuario no puede ser nulo");
        }
        if (IdTarjeta == null || IdTarjeta.toString().trim().isEmpty()) {
            throw new Exception(" El Id no puede ser nulo");
        }

        if (pin == null || pin.toString().trim().isEmpty() || pin.toString().length() > 4) {
            throw new Exception(" El pin es nulo o no lo esta ingresando bien");
        }

    }

    public void validacion2(Double Saldo, LocalDate fechaDeVencimiento, Boolean Alta) throws Exception {
        if (Saldo < 0 || Saldo == null) {
            throw new Exception(" Su cuenta esta vacia");
        }
        LocalDate fechaActual = LocalDate.now();
        if (fechaActual.isAfter(fechaDeVencimiento)) {
            System.out.println("La fecha de vencimiento ha pasado");
        }
        if (Alta == null || Alta == false) {
            throw new Exception(" La tarjeta esta dada de baja");
        }
    }

}
