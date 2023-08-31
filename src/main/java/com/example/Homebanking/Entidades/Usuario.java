/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Entidades;

import com.example.Homebanking.Enumeraciones.Rol;
<<<<<<< Updated upstream
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
=======
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
>>>>>>> Stashed changes
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Fabi
 */
@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String IdUsuario;
    
    @Column(nullable= false)
    String nombre;
    
    @Column(nullable= false)
    String apellido;
    
    @OneToOne
    protected Cuenta cuenta;
    
    @Column(nullable= false)
    String clave;//TIENE UNA CLAVE ESPECIFICA PARA ENTRAR COMO ADMINISTRADOR
    
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tarjeta_debito_id")
    TarjetaSuperClass tarjetaDebito;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tarjeta_credito_id")
    TarjetaSuperClass tarjetaCredito;
    
    
    Boolean Alta;
    
   
    LocalDate fechaAlta;
    
    @Column(unique = true)
    String email;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
     @Column(unique = true)
    String DNI;

}
