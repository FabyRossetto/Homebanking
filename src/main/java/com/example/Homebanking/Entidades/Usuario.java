package com.example.Homebanking.Entidades;

import com.example.Homebanking.Enumeraciones.Rol;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idUsuario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @OneToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "idCuenta") // Especifica la columna en la base de datos que representa la relación
    private Cuenta Cuenta;

//    @Column(nullable= false )
//    String clave;//TIENE UNA CLAVE ESPECIFICA PARA ENTRAR COMO ADMINISTRADOR
    @Column(name = "clave")
    private String clave;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tarjeta_debito_id")
    private TarjetaSuperClass tarjetaDebito;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tarjeta_credito_id")
    private TarjetaSuperClass tarjetaCredito;

    private Boolean Alta;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(unique = true)
    private String DNI;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the Cuenta
     */
    public Cuenta getCuenta() {
        return Cuenta;
    }

    /**
     * @param Cuenta the Cuenta to set
     */
    public void setCuenta(Cuenta Cuenta) {
        this.Cuenta = Cuenta;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the tarjetaDebito
     */
    public TarjetaSuperClass getTarjetaDebito() {
        return tarjetaDebito;
    }

    /**
     * @param tarjetaDebito the tarjetaDebito to set
     */
    public void setTarjetaDebito(TarjetaSuperClass tarjetaDebito) {
        this.tarjetaDebito = tarjetaDebito;
    }

    /**
     * @return the tarjetaCredito
     */
    public TarjetaSuperClass getTarjetaCredito() {
        return tarjetaCredito;
    }

    /**
     * @param tarjetaCredito the tarjetaCredito to set
     */
    public void setTarjetaCredito(TarjetaSuperClass tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
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

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the rol
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * @return the DNI
     */
    public String getDNI() {
        return DNI;
    }

    /**
     * @param DNI the DNI to set
     */
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    /**
     * @return the quieroNotificaciones
     */
    public boolean isQuieroNotificaciones() {
        return quieroNotificaciones;
    }

    /**
     * @param quieroNotificaciones the quieroNotificaciones to set
     */
    public void setQuieroNotificaciones(boolean quieroNotificaciones) {
        this.quieroNotificaciones = quieroNotificaciones;
    }

    private boolean quieroNotificaciones;

    public Usuario get() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isPresent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
