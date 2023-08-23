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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



@Service
public class UsuarioServicio implements UserDetailsService{

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
    
    @Autowired
    @Qualifier("tarjetaServicio")
    TarjetaServicio tarjeta;

    //se registra el usuario con sus datos personales en este metodo 
    @Transactional
    public Usuario crear(String nombre, String apellido, String Email, String clave,String DNI) throws ErrorServicio, Exception {
       
            Usuario encontrar = usuarioRepositorio.findByEmail(Email);
            if (encontrar != null) {

                if (encontrar.getEmail().equals(Email)) {
                    if (encontrar.getAlta() == false) {
                        encontrar.setAlta(true);
                        throw new ErrorServicio("Este e-mail ya se encuentra registrado y el usuario se ha dado de alta nuevamente");
                    }
                }
            } else {
        
        validar(nombre, apellido, Email, clave,DNI);

        Usuario usuario = new Usuario();
        usuario.getIdUsuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(Email);
        String claveEnc = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(claveEnc);//se guarda la clave encriptada en la base de datos
        usuario.setAlta(Boolean.TRUE);
        usuario.setFechaAlta(new Date());
        usuario.setDNI(DNI);

        if (usuario.getClave()=="ADMIN1234") {
            usuario.setRol(Rol.ADMINISTRADOR);
        } else {
            usuario.setRol(Rol.USUARIO);
        }
         return usuarioRepositorio.save(usuario);

    }
            
       return encontrar;
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
                    
                    usu.setTarjetaDebito(debito);
                

                
                    TarjetaSuperClass credito = tarjetaCredito.CrearTarjeta(clave);
                    
                    usu.setTarjetaCredito(credito);
                }
                
                usuarioRepositorio.save(usu);
            }

        }

    

    //se modifican los datos personales del usuario. Para modificar tarjetas y cuenta tienen sus propios metodos
    public void modificarDatosPersonales(String IdUsuario, String nombre, String apellido, String Email, String clave,String DNI) throws ErrorServicio, Exception {
        validar(nombre,apellido,Email,clave,DNI);

        Optional<Usuario> usuario = usuarioRepositorio.findById(IdUsuario);

        if (usuario.isPresent()) {
            Usuario usu = usuario.get();
            usu.setNombre(nombre);
            usu.setApellido(apellido);
            usu.setEmail(Email);
            String claveEnc = new BCryptPasswordEncoder().encode(clave);
            usu.setClave(claveEnc);
            usu.setDNI(DNI);

            usuarioRepositorio.save(usu);

        } else {
            throw new ErrorServicio("No se encontró o no se pudo modificar el usuario solicitado");
        }
    }
    //este metodo le toca hacer a Giani
//     public int enviar(String mail) throws ErrorServicio {
//        int codigoDeRecuperacion = (int) (Math.random() * 9000 + 1);
//        ns.enviar("Usted esta queriendo cambiar su contraseña de RecetApp", "Su código de recuperacion es " + codigoDeRecuperacion, mail);
//        return codigoDeRecuperacion;
//    }

    @Transactional
    public void cambiarContraseña(Integer codigoIngresado, String claveNueva, String email) throws ErrorServicio {
        try {

            Usuario usu = usuarioRepositorio.findByEmail(email);

            String claveEnc = new BCryptPasswordEncoder().encode(claveNueva);
            usu.setClave(claveEnc);
            usuarioRepositorio.save(usu);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorServicio("No se ha podido cambiar la contraseña.");
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

    //se elimina al usuario, asi como la cuenta y sus tarjetas de debito y credito
    
    public void EliminarUsuario(String IdUsuario) throws Exception {
        //primero debo eliminar los objetos relacionados,como la cuenta y las tarjetas.
        //probar cada metodo por separado,el de eliminar cuenta no funciona.y no se puede eliminar el usuario.

        Usuario usuario = usuarioRepositorio.getById(IdUsuario);

//        if (usuario.getTarjetaCredito() != null) {
//            tarjeta.EliminarTarjeta(usuario.getTarjetaCredito().getId());
//        }
//        if (usuario.getTarjetaDebito() != null) {
//            tarjeta.EliminarTarjeta(usuario.getTarjetaDebito().getId());
//        }
//        if (usuario.getCuenta() != null) {
//            cuentaSer.borrarPorId(usuario.getCuenta().getId());
//        }

        //ver que funcionen los metodos por separado.
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
    public void validar(String nombre, String apellido, String Email, String clave,String DNI) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del usuario no puede ser nulo");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido del usuario no puede ser nulo");
        }
        if (Email == null || Email.isEmpty()) {
            throw new ErrorServicio("El email del usuario no puede ser nulo");
        }
        if (clave == null || clave.isEmpty() || clave.length() < 6) {
            throw new ErrorServicio("La clave del usuario no puede ser nula y tiene que tener 6 digitos o mas");
        }
        if (DNI == null || DNI.isEmpty() || DNI.length() < 7) {
            throw new ErrorServicio("El DNI no es correcto");
        }

    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {//este metodo recibe el nombre de usuario lo busca en el repositorio y lo transforma en un usuario de spring security
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());
            permisos.add(p1);

//            Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            //el ultimo parametro solicita una lista de permisos
            User user = new User(usuario.getEmail(), usuario.getClave(), permisos);
            return user;

        } else {
            return null;
        }
}
}
