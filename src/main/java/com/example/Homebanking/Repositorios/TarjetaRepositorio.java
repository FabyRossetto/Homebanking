/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Repositorios;

import com.example.Homebanking.Entidades.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Fabi
 */
@Repository
public interface TarjetaRepositorio extends JpaRepository<Tarjeta,Long> {
    
    @Query("SELECT t FROM Tarjeta t WHERE t.IdTarjeta = :IdTarjeta ")
    public Tarjeta buscarPorId(@Param("IdTarjeta") Long IdTarjeta);
}
