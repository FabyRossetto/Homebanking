/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 *
 * @author Fabi
 */
@Data
@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long Id;
    
    
     Boolean Alta= Boolean.TRUE; 
     
     
    
     //saldoActual=saldo+nuevoIngreso
    Double Saldo;
    Double saldoActual;
   
    Double ingreso;
    Double retiro;
    
    @OneToMany
    Transferencia transferencia;
    
    @OneToOne
    Usuario usuario;
    
}
