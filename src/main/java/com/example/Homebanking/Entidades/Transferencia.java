package com.example.Homebanking.Entidades;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import org.springframework.stereotype.Component;


/**
 *
 * @author Fabi
 */
@Data
@Entity
@Component
public class Transferencia implements Serializable{

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

   
   @OneToOne
   Cuenta CuentaEmisora;

   @OneToOne
   Cuenta CuentaReceptora;
    
    LocalDate Fecha;

    @Column(nullable = false)
    Double monto;

    public Transferencia() {
    }
    
    public Transferencia(Double monto) {
    this.monto = monto;
    }

    public Transferencia(Long Id, Cuenta CuentaEmisora, Cuenta CuentaReceptora, LocalDate Fecha, Double monto) {
        this.Id = Id;
        this.CuentaEmisora = CuentaEmisora;
        this.CuentaReceptora = CuentaReceptora;
        this.Fecha = Fecha;
        this.monto = monto;
    }  

}
