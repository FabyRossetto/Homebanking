/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Entidades;

import javax.persistence.Entity;
import lombok.Data;

/**
 *
 * @author Fabi
 */


public class TarjetaDebitoSubClass extends TarjetaSuperClass{

    
       public TarjetaDebitoSubClass(String idUsuario, String clave) {
        super(idUsuario, clave);
        
    }

    public TarjetaDebitoSubClass() {
        super();
    }

    public TarjetaDebitoSubClass(TarjetaSuperClass CrearTarjeta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
