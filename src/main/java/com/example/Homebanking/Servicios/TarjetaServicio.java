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
 * Cuando hagamos la autorizacion de usuarios, es importante que a algunos
 * metodos solo puedan acceder los administradores: como modificar,eliminar o
 * dar de baja.
 *
 */
@Service
public class TarjetaServicio {

    @Autowired
    TarjetaSuperClass tarjeta;

    @Autowired
    TarjetaRepositorio tarjetaRepo;

    @Autowired
    UsuarioRepositorio ure;

    @Transactional
    public TarjetaSuperClass CrearTarjeta(String IdUsuario, Integer pin) throws Exception {//repensar el parametro idTarjeta,creo q no es necesario

        Usuario usuario = ure.getById(IdUsuario);
        if (usuario.getTarjetaDebito()== null) {
            tarjeta.setUsuario(usuario);
           
            tarjeta.setPin(pin);
            tarjeta.setFechaVencimiento(LocalDate.of(2028, 12, 31));
            tarjeta.setTipo(tarjeta.getTipo());

        }
        return tarjetaRepo.save(tarjeta);
    }

    @Transactional
    public TarjetaSuperClass modificarTarjeta(Long IdTarjeta, String IdUsuario, Integer pin) throws Exception {

        TarjetaSuperClass trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
        Usuario usuario = ure.getById(IdUsuario);

        if (trayendoTarjeta != null) {
            trayendoTarjeta.setUsuario(usuario);
            trayendoTarjeta.setPin(pin);
            trayendoTarjeta.setSaldo(usuario.getCuenta().getSaldo());
            trayendoTarjeta.setFechaVencimiento(LocalDate.of(2035, 12, 31));
            trayendoTarjeta.setTipo(tarjeta.getTipo());

        }
        return tarjetaRepo.save(trayendoTarjeta);
    }

    public void EliminarTarjeta(Long IdTarjeta) {
        TarjetaSuperClass trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
        if (trayendoTarjeta != null) {
            tarjetaRepo.delete(trayendoTarjeta);
        }
    }

    @Transactional
    public void DarDeBajaTarjeta(Long IdTarjeta) {

        TarjetaSuperClass trayendoTarjeta = tarjetaRepo.buscarPorId(IdTarjeta);
        if (trayendoTarjeta != null || trayendoTarjeta.getAlta() == true) {
            trayendoTarjeta.setAlta(false);

        }

    }

    public void validacion1(String IdUsuario, Long Id, Integer pin) throws Exception {
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
