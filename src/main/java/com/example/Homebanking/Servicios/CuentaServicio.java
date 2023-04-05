
package com.example.Homebanking.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaServicio {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private TransferenciaRepositorio transferenciaRepositorio;
    
  //el error se debe a que no tengo creado los repo de usuario y transfernecia 
    
}
