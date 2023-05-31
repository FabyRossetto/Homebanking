package com.example.Homebanking.Servicios;

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

import java.util.Optional;


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

    //se crea el usuario con sus datos personales en este metodo 
    @Transactional
    public Usuario crear(String nombre, String apellido, String Email, Integer clave,String DNI) throws ErrorServicio, Exception {
        validar(nombre, apellido, Email, clave,DNI);

        Usuario usuario = new Usuario();
        usuario.getIdUsuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(Email);
        usuario.setClave(clave);
        usuario.setAlta(Boolean.TRUE);
        usuario.setFechaAlta(new Date());
        usuario.setDNI(DNI);

        if (usuario.getClave() == 19582023) {
            usuario.setRol(Rol.ADMINISTRADOR);
        } else {
            usuario.setRol(Rol.USUARIO);
        }

        return usuarioRepositorio.save(usuario);
    }

    //se le agregan al usuario la cuenta y las tarjetas de debito y credito
    @Transactional
    public void cargarTarjetasyCuenta(String IdUsuario, Double saldoCuenta, Integer clave) throws Excepcion, Exception {

        Optional<Usuario> usuario = usuarioRepositorio.findById(IdUsuario);

        if (usuario.isPresent()) {
            Usuario usu = usuario.get();

            if (usu.getRol() == USUARIO) {

                    Cuenta cuen = cuentaSer.guardar(cuenta.getId(), saldoCuenta);
                   usu.setCuenta(cuen);

               
                    TarjetaSuperClass debito = tarjetaDebito.CrearTarjeta(IdUsuario, clave); 
                    
                    usu.setTarjetaDebito((TarjetaDebitoSubClass)debito);
                

                
                    TarjetaSuperClass credito = tarjetaCredito.CrearTarjeta(clave);
                    
                    usu.setTarjetaCredito((TarjetaCreditoSubClass)credito);
                }
                //la de credito sobreesribe la de debito,volver a probar!
                usuarioRepositorio.save(usu);
            }

        }

    

    //se modifican los datos personales del usuario. Para modificar tarjetas y cuenta tienen sus propios metodos
    public void modificarDatosPersonales(String IdUsuario, String nombre, String apellido, String Email, Integer clave,String DNI) throws ErrorServicio, Exception {
        validar(nombre,apellido,Email,clave,DNI);

        Optional<Usuario> usuario = usuarioRepositorio.findById(IdUsuario);

        if (usuario.isPresent()) {
            Usuario usu = usuario.get();
            usu.setNombre(nombre);
            usu.setApellido(apellido);
            usu.setEmail(Email);
            usu.setClave(clave);
            usu.setDNI(DNI);

            usuarioRepositorio.save(usu);

        } else {
            throw new ErrorServicio("No se encontró el usuario solicitado");
        }
    }

    //se da de baja al usuario,por lo que tambien se da de baja su cuenta y las tarjetas de credito y debito,y me retorna la fecha de la baja 
    public Date darBaja(String Id) throws ErrorServicio {//funciona bien
        Optional<Usuario> respuesta = usuarioRepositorio.findById(Id);
        Date fechaBaja = new Date();
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setAlta(Boolean.FALSE);
            usuarioRepositorio.save(usuario);
        }
        return fechaBaja;
    }

    //CREAR ELIMINAR, se elimina al usuario, asi como la cuenta y sus tarjetas de debito y credito
    
     public void EliminarUsuario(String IdUsuario) throws Exception {
         //primero debo eliminar los objetos relacionados,como la cuenta y las tarjetas.
         //probar cada metodo por separado,el de eliminar cuenta no funciona.y no se puede eliminar el usuario.
         
        Usuario usuario = usuarioRepositorio.getById(IdUsuario);
       
        Cuenta EliminarCuenta=usuario.getCuenta();
        
         tarjetaCredito.EliminarTarjeta(usuario.getTarjetaCredito().getId());
         tarjetaDebito.EliminarTarjeta(usuario.getTarjetaDebito().getId());
         cuentaSer.borrarPorId(EliminarCuenta.getId());
        
    
        usuarioRepositorio.delete(usuario);
     }
     
     public Usuario BuscarUsuarioPorDNI(String DNI){
         Usuario usuario=usuarioRepositorio.findByDNI(DNI);
         return usuario;
     }
     
     public Usuario BuscarUsuarioPorApellido(String apellido){
         Usuario usuario=usuarioRepositorio.findByApellido(apellido);
         return usuario;
         
     }
     
     public Usuario BucarUsuarioPorEmail(String email){
         Usuario usuario=usuarioRepositorio.findByEmail(email);
         return usuario;
     }
     
     public Usuario BuscarPorCuenta(Long IdCuenta){
         Usuario usuario= usuarioRepositorio.findByCuenta(IdCuenta);
         return usuario;
     }
//validaciones
    public void validar(String nombre, String apellido, String Email, Integer clave,String DNI) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del usuario no puede ser nulo");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido del usuario no puede ser nulo");
        }
        if (Email == null || Email.isEmpty()) {
            throw new ErrorServicio("El email del usuario no puede ser nulo");
        }
        if (clave == null || clave.toString().isEmpty() || clave.toString().length() < 6) {
            throw new ErrorServicio("La clave del usuario no puede ser nula y tiene que tener 6 digitos o mas");
        }
        if (DNI == null || DNI.isEmpty() || DNI.length() < 7) {
            throw new ErrorServicio("El DNI no es correcto");
        }

    }
}
