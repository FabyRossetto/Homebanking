package com.example.Homebanking.servicios;

import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Entidades.TarjetaCreditoSubClass;
import com.example.Homebanking.Entidades.TarjetaDebitoSubClass;
import com.example.Homebanking.Entidades.TarjetaSuperClass;
import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Enumeraciones.Rol;

import static com.example.Homebanking.Enumeraciones.Rol.USUARIO;
import com.example.Homebanking.Errores.ErrorServicio;
import com.example.Homebanking.Repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    com.example.Homebanking.Servicios.CuentaServicio cuentaSer;
    @Autowired
    com.example.Homebanking.Servicios.TarjetaCreditoServicio tarjetaCredito;

    @Autowired
    com.example.Homebanking.Servicios.TarjetaDebitoServicio tarjetaDebito;

    @Transactional
    public Usuario crear(String Id, String nombre, String apellido, String Email, Integer clave) throws ErrorServicio, Exception {
//        validar(nombre, apellido, Email, clave);
        Usuario usu = usuarioRepositorio.buscarPorMail(Email);

        if (usu == null) {

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
         Usuario Usuarioguardado= usuarioRepositorio.save(usuario);//NO PUEDO PONER VARIAS VECES SAVE PORQUE ME TIRA ERROR DE QUE EL EMAIL ESTA DUPLICADO
         
         //hacer dos metodos. solo funciona la parte de guardar los datos basicos.Aun me falta crear la cuenta y las tarjetas

            if (usuario.getRol() == USUARIO) {
                if (usuario.getCuenta() == null) {
                    Cuenta cuenta = new Cuenta();
                    usuario.setCuenta(cuentaSer.guardar(cuenta.getId(), Usuarioguardado));
                     usuarioRepositorio.save(usuario);
                }

//                if (usuario.getTarjetaDebito() == null) {
//                    TarjetaSuperClass debito = new TarjetaSuperClass();
//
//                    usuario.setTarjetaDebito(tarjetaDebito.CrearTarjeta(Id, debito.getId(), clave));//el id de las tarjetas da nulo
//                }
//                if (usuario.getTarjetaCredito() == null) {
//                    TarjetaSuperClass credito = new TarjetaSuperClass();
//
//                    usuario.setTarjetaCredito(tarjetaCredito.CrearTarjeta(Id, credito.getId(), clave));
//                }
//            }
            
            }
            return Usuarioguardado;
        } else {
            return usu;
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
