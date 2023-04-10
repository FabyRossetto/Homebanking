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

/**
 *
 * @author Fabi
 */
@Data
@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    Long Id;
    
    
    Boolean Alta=Boolean.TRUE; 
    
    Double Saldo;
   
    @OneToMany
    List<Transferencia> transferencia;//tuve que cambiar esto porque no me dejaba mapearla con el oneToMany al ser transferencia un solo objeto.
    
    @OneToOne
    Usuario usuario;
    
}
