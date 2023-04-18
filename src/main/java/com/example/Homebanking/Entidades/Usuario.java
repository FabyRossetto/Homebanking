/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Entidades;

import com.example.Homebanking.Enumeraciones.Rol;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    String IdUsuario;
    
    @Column(nullable= false)
    String nombre;
    
    @Column(nullable= false)
    String apellido;
    
    @OneToOne
    Cuenta Cuenta;
    
    @Column(nullable= false)
    Integer clave;//TIENE UNA CLAVE ESPECIFICA PARA ENTRAR COMO ADMINISTRADOR
    
    
    @OneToOne
    TarjetaSuperClass tarjetaDebito;
    
    @OneToOne
    TarjetaSuperClass tarjetaCredito;
    
    
    Boolean Alta;
    
    @Temporal(TemporalType.TIMESTAMP)
    Date fechaAlta;
    
    @Column(unique = true)
    String email;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;
    

}
