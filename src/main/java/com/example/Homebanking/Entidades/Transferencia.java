package com.example.Homebanking.Entidades;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 *
 * @author Fabi
 */
@Data
@Entity
@Component
public class Transferencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @Column(nullable = false)
    String DNIEmisor;

    @Column(nullable = false)
    String DNIReceptor;

    LocalDate Fecha;

    @Column(nullable = false)
    Double monto;

    public Transferencia(Double monto) {
        this.monto = monto;
    }

    public Transferencia() {
    }

    public Transferencia(Long Id, String DNIEmisor, String DNIReceptor, LocalDate Fecha, Double monto) {
        this.Id = Id;
        this.DNIEmisor = DNIEmisor;
        this.DNIReceptor = DNIReceptor;
        this.Fecha = Fecha;
        this.monto = monto;
    }
    
    

}
