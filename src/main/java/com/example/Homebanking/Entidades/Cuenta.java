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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Boolean Alta = Boolean.TRUE;

    //saldoActual=saldo+deposito
    private Double Saldo;
    private Double saldoActual;

    private Double deposito = 0.00;
    private Double extraccion = 0.00;
    
    @Temporal(TemporalType.TIMESTAMP)   
    private Date fechaAlta;

//    @OneToMany
//    Transferencia transferencia;

//    @OneToOne
//    Usuario usuario;
//  

    /**
     * @return the Id
     */
    public Long getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(Long Id) {
        this.Id = Id;
    }

    /**
     * @return the Alta
     */
    public Boolean getAlta() {
        return Alta;
    }

    /**
     * @param Alta the Alta to set
     */
    public void setAlta(Boolean Alta) {
        this.Alta = Alta;
    }

    /**
     * @return the Saldo
     */
    public Double getSaldo() {
        return Saldo;
    }

    /**
     * @param Saldo the Saldo to set
     */
    public void setSaldo(Double Saldo) {
        this.Saldo = Saldo;
    }

    /**
     * @return the saldoActual
     */
    public Double getSaldoActual() {
        return saldoActual;
    }

    /**
     * @param saldoActual the saldoActual to set
     */
    public void setSaldoActual(Double saldoActual) {
        this.saldoActual = saldoActual;
    }

    /**
     * @return the deposito
     */
    public Double getDeposito() {
        return deposito;
    }

    /**
     * @param deposito the deposito to set
     */
    public void setDeposito(Double deposito) {
        this.deposito = deposito;
    }

    /**
     * @return the extraccion
     */
    public Double getExtraccion() {
        return extraccion;
    }

    /**
     * @param extraccion the extraccion to set
     */
    public void setExtraccion(Double extraccion) {
        this.extraccion = extraccion;
    }

    /**
     * @return the fechaAlta
     */
    public Date getFechaAlta() {
        return fechaAlta;
    }

    /**
     * @param fechaAlta the fechaAlta to set
     */
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
    
}