/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long Id;

    Boolean Alta = Boolean.TRUE;

    //saldoActual=saldo+deposito
    
    Double saldo;

    Double deposito = 0.00;
    Double extraccion = 0.00;
    
    @Temporal(TemporalType.TIMESTAMP)   
    Date fecha;

    @OneToMany
    List <Transferencia> listaTransferencias;

//    @OneToOne
//    protected Usuario usuario;

}
