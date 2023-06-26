package com.example.Homebanking.Entidades;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Transferencia {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    /*@ManyToOne
    @JoinColumn (name="Id")
    Cuenta CuentaEmisora;
    // nested exception is org.hibernate.AnnotationException:@Column(s) not allowed on a @OneToOne property: com.example.Homebanking.Entidades.Transferencia.CuentaEmisora
*/
   @OneToOne
    Cuenta CuentaReceptora;
    
    @Temporal(TemporalType.TIMESTAMP)
    Date Fecha;

    @Column(nullable = false)
    Double monto;

}
