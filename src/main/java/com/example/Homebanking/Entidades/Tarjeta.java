/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Entidades;

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
public class Tarjeta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long Id;
    
    Double SaldoCredito=500000.00;//saldo maximo a gastar
    
    Double SaldoDebito;
    
    @Column(nullable= false)
    Integer pin;
    
    @Temporal(TemporalType.DATE)
    Date fechaVencimiento;
    //2023/07/25(año,mes,dia).
    
    @OneToOne
    Usuario usuario;
    
    Boolean Alta=Boolean.TRUE;
    
}
