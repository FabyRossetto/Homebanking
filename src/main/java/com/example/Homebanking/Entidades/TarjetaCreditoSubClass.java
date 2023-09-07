/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Entidades;


/**
 *
 * las clases subclass tienen solo constructores porque todo lo demas lo heredan de su clase padre
 */

public class TarjetaCreditoSubClass extends TarjetaSuperClass{
    
    public TarjetaCreditoSubClass(String clave) {
        super(clave);
}

    public TarjetaCreditoSubClass() {
    }
    
}