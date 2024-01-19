
package com.example.Homebanking.Entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UsuarioLogin {
     @Id
     private String email;
    private String clave;

    // Constructores, getters y setters

    public UsuarioLogin() {
    }

    public UsuarioLogin(String email, String clave) {
        this.email = email;
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}

