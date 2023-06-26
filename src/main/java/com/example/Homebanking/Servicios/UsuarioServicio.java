package com.example.Homebanking.servicios;

import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Entidades.TarjetaCreditoSubClass;
import com.example.Homebanking.Entidades.TarjetaDebitoSubClass;
import com.example.Homebanking.Entidades.Usuario;

import com.example.Homebanking.Entidades.TarjetaSuperClass;

import com.example.Homebanking.Enumeraciones.Rol;

import static com.example.Homebanking.Enumeraciones.Rol.USUARIO;
import com.example.Homebanking.Errores.ErrorServicio;
import com.example.Homebanking.Errores.Excepcion;
import com.example.Homebanking.Repositorios.UsuarioRepositorio;
import java.util.Date;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Homebanking.Servicios.CuentaServicio;
import java.util.Optional;
//Usuario Servicio 
//crearUsuario
//eliminarUsuario
//darBaja
//modificarUsuario
//enviarEmail
//cambiarContraseña
//validaciones

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    CuentaServicio cuentaSer;

    @Autowired
    com.example.Homebanking.Servicios.TarjetaCreditoServicio tarjetaCredito;

    @Autowired
    com.example.Homebanking.Servicios.TarjetaDebitoServicio tarjetaDebito;
    
    @Autowired
    com.example.Homebanking.Entidades.Cuenta cuenta;

    @Transactional
    public Usuario crear(String Id, String nombre, String apellido, String Email, Integer clave) throws ErrorServicio, Exception {
//        validar(nombre, apellido, Email, clave);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(Id);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(Email);
        usuario.setClave(clave);
        usuario.setAlta(Boolean.TRUE);
        usuario.setFechaAlta(new Date());

        if (usuario.getClave() == 19582023) {
            usuario.setRol(Rol.ADMINISTRADOR);
        } else {
            usuario.setRol(Rol.USUARIO);
        }

        return usuarioRepositorio.save(usuario);
    }

    public void cargarTarjetasyCuenta(String IdUsuario, Double saldoCuenta,Integer clave) throws Excepcion, Exception {

        Optional<Usuario> usuario = usuarioRepositorio.findById(IdUsuario);
        
        if (usuario.isPresent()) {
            Usuario usu = usuario.get();
            
            if (usu.getRol() == USUARIO) {
               
                Cuenta cuen = cuentaSer.guardar(cuenta.getId(), saldoCuenta);
                   usu.setCuenta(cuen);
               
        //      TarjetaSuperClass credito = tarjetaCredito.CrearTarjeta(clave);
                    
          //          usu.setTarjetaCredito(credito);
          //      }
                
                usuarioRepositorio.save(usu);;
            }
             
        }  

        }
    
        //    
        //        public void modificar (String Id, String nombre, String apellido, String Email, String clave) throws ErrorServicio { 
        //        validar(nombre,apellido,Email,clave);
        //        
        //         Optional <Usuario> respuesta= usuarioRepositorio.findById(Id);
        //         if(respuesta.isPresent()){
        //        
        //        Usuario usuario = respuesta.get();
        //        usuario.setNombre(nombre);
        //        usuario.setApellido(apellido);
        //        usuario.setEmail(Email);
        //        //usuario.setClave (clave);
        //        /*Luego guardamos en el repositorio los datos del usuario modificados*/
        //        usuarioRepositorio.save(usuario);
        //    }else{
        //             throw new ErrorServicio ("No se encontró el usuario solicitado");
        //         }
        //             }
        //    public void darBaja (String Id)throws ErrorServicio{
        //        Optional <Usuario> respuesta= usuarioRepositorio.findById(Id);
        //         if(respuesta.isPresent()){
        //        Usuario usuario = respuesta.get();
        //        //usuario.setBaja(new Date());
        //         usuarioRepositorio.save(usuario);
        //    }
        //    
        //    }

    public void validar(String nombre, String apellido, String Email, Integer clave) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del usuario no puede ser nulo");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido del usuario no puede ser nulo");
        }
        if (Email == null || Email.isEmpty()) {
            throw new ErrorServicio("El mail del usuario no puede ser nulo");
        }
        if (clave == null || clave.toString().isEmpty() || clave.toString().length() < 6) {
            throw new ErrorServicio("La clave del usuario no puede ser nula y tiene que tener 6 digitos o mas");
        }

    }
}
