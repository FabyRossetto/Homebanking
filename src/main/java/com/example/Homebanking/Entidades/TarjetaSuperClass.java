/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Entidades;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
   protected Long Id;
    
   protected Double Saldo;
    
    @Column(nullable= false)
   protected Integer pin;
    
    
    protected LocalDate fechaVencimiento;
    //2023/07/25(año,mes,dia).
    
    @OneToOne
    protected Usuario usuario;
    
    protected Boolean Alta=Boolean.TRUE;
    
    protected String tipo;
}
