package com.example.Homebanking.servicios;

import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Errores.ErrorServicio;
import com.example.Homebanking.Repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
    
    @Autowired 
    private UsuarioRepositorio usuarioRepositorio;
     @Autowired 
    private NotificacionServicio notificacionServicio;
    @Transactional
    public void crear (String nombre, String apellido, String Email, String clave)throws ErrorServicio {
        validar(nombre,apellido,Email,clave);
       
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(Email);
        //usuario.setClave(clave);
        //usuario.setAlta(new Date());
        
        usuarioRepositorio.save(usuario);
        
        notificacionServicio.eviar("Bienvenidos al mejor Homebanking", "Homebanking", usuario.getEmail());
    }
        public void modificar (String Id, String nombre, String apellido, String Email, String clave) throws ErrorServicio { 
        validar(nombre,apellido,Email,clave);
        
         Optional <Usuario> respuesta= usuarioRepositorio.findById(Id);
         if(respuesta.isPresent()){
         /*En este caso no debemos crear un nuevo usuario porque ya lo tenemos creado
    sino que debemos ir al repositorio a buscarlo por el ID y luego ponemos un método 
    get para traer el usuario que encontró. Luego seteamos los datos que queremos modificar*/
        Usuario usuario = respuesta.get();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(Email);
        //usuario.setClave (clave);
        /*Luego guardamos en el repositorio los datos del usuario modificados*/
        usuarioRepositorio.save(usuario);
    }else{
             throw new ErrorServicio ("No se encontró el usuario solicitado");
         }
             }
    public void darBaja (String Id)throws ErrorServicio{
        Optional <Usuario> respuesta= usuarioRepositorio.findById(Id);
         if(respuesta.isPresent()){
        Usuario usuario = respuesta.get();
        //usuario.setBaja(new Date());
         usuarioRepositorio.save(usuario);
    }
    
    }
    private void validar (String nombre, String apellido, String Email, String clave) throws ErrorServicio{
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del usuario no puede ser nulo");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido del usuario no puede ser nulo");
        }
        if (Email == null || Email.isEmpty()) {
            throw new ErrorServicio("El mail del usuario no puede ser nulo");
        }
        if (clave == null || clave.isEmpty() || clave.length() < 6) {
            throw new ErrorServicio("La clave del usuario no puede ser nula y tiene que tener 6 digitos o mas");
        }
    
    }
}