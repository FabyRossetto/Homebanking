/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.TemporalType;
import java.util.Date;
<<<<<<< Updated upstream
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
=======
import lombok.Data;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Component;
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
   
    @OneToMany
    List<Transferencia> transferencia;//tuve que cambiar esto porque no me dejaba mapearla con el oneToMany al ser transferencia un solo objeto.
=======
    Double saldoActual;

    Double deposito = 0.00;
    Double extraccion = 0.00;
    
   // @Temporal(TemporalType.TIMESTAMP)   
    Date fecha;

//    @OneToMany
//    Transferencia transferencia;

//    @OneToOne
//    Usuario usuario;
//  
>>>>>>> Stashed changes
    
    @OneToOne
    Usuario usuario;
    
}
