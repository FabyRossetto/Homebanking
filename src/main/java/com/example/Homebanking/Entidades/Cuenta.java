/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Entidades;

import java.io.Serializable;
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
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Data
@Entity
@Component
public class Cuenta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuenta;

    private Boolean Alta = Boolean.TRUE;

    //saldoActual=saldo+deposito
    private Double saldo;
    private Double saldoActual;

    private Double deposito = 0.00;
    private Double extraccion = 0.00;
    
    @Temporal(TemporalType.TIMESTAMP)   
    private Date fechaAlta;



   
    public Long getIdCuenta() {
        return idCuenta;
    }

    
    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

 
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
        return saldo;
    }

    /**
     * @param Saldo the Saldo to set
     */
    public void setSaldo(Double Saldo) {
        this.saldo = Saldo;
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