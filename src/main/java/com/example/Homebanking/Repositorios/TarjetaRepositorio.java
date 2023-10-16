/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Repositorios;

import com.example.Homebanking.Entidades.TarjetaSuperClass;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Fabi
 */
@Repository
public interface TarjetaRepositorio extends JpaRepository<TarjetaSuperClass,Long> {
    
    @Query("SELECT t FROM TarjetaSuperClass t WHERE t.Id = :IdTarjeta ")
    public TarjetaSuperClass buscarPorId(@Param("IdTarjeta") Long Id);

     @Query("SELECT t FROM TarjetaSuperClass t WHERE t.fechaVencimiento = :fechaVto ")
    public List <TarjetaSuperClass> buscarPorVto(@Param("fechaVto") LocalDate fechaVto);
}
