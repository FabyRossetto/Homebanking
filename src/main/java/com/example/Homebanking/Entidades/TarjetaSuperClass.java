/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Entidades;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 *
 * @author Fabi
 */
@Data
@Entity
@Component
public class TarjetaSuperClass {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   protected Long Id;
    
   protected Double Saldo;
    
    @Column(nullable= false)
   protected Integer pin;
    
    protected LocalDate fechaVencimiento;
    //2023/07/25(año,mes,dia).
    
   
    protected Boolean Alta=Boolean.TRUE;
    
    protected String tipo;

    public TarjetaSuperClass() {
    }
    

    public TarjetaSuperClass(Long Id, Double Saldo, Integer pin, LocalDate fechaVencimiento, String tipo) {
        this.Id = Id;
        this.Saldo = Saldo;
        this.pin = pin;
        this.fechaVencimiento = fechaVencimiento;
        this.tipo = tipo;
    }
     public TarjetaSuperClass(String idUsuario, String clave) {
        
    }
     
      public TarjetaSuperClass(String clave) {
        
    }
    
}
