package com.example.Homebanking.Servicios;

import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Entidades.Transferencia;
import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Errores.Excepcion;
import com.example.Homebanking.Repositorios.CuentaRepositorio;
import com.example.Homebanking.Repositorios.TransferenciaRepositorio;
import com.example.Homebanking.Repositorios.UsuarioRepo;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaServicio {
    
    @Autowired
    private NotificacionServicio notificacionServicio;

    @Autowired
    private CuentaRepositorio cuentaRepositorio;
    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private TransferenciaRepositorio transferenciaRepositorio;

    //el error se debe a que no tengo creado los repo de usuario y transfernecia 
    //GUARDAR UNA CUENTA: CREACIÓN (necesito transferencia para crearlo?)
//    @Transactional
//    public Cuenta guardar(Long Id, Usuario usuario, Double saldo, Boolean alta) throws Excepcion {
//
//        //SETEO DE ATRIBUTOS
//        Cuenta cuenta = new Cuenta();
//        cuenta.setUsuario(usuario);
//        cuenta.setSaldo(Double.NaN);
//        cuenta.setAlta(alta);
//        // cuenta.setFecha(fecha);
//        cuenta.setId(Id);
//
//        //cuenta.setTransferencia(transferencia);
//        //PERSISTENCIA DEL OBJETO
//        return cuentaRepositorio.save(cuenta);
//
//    }
    @Transactional //saco el IdUsuario
    public Cuenta guardar(Long Id, Double saldo) throws Excepcion {
        //Optional<Usuario> usu=usuarioRepo.findById(Idusuario);
        // Usuario usuario= usu.get();
        //  if(usuario.getCuenta()==null){
        //SETEO DE ATRIBUTOS
        Cuenta cuenta = new Cuenta();
        // cuenta.setUsuario(usuario);
        cuenta.setSaldo(saldo);
        cuenta.setAlta(Boolean.TRUE);
        cuenta.setFecha(new Date());
        cuenta.setId(Id);

        return cuentaRepositorio.save(cuenta);
        
//        notificacionServicio.enviar("Gracias por elegirnos", "HomeBanking", mail); deberia ir en usuario no acá

//    }else{
//            return usuario.getCuenta();
    }

//    //ELIMINAR CUENTA
    @Transactional
    public void borrarPorId(Long Id) throws Excepcion {
        Optional<Cuenta> optional = cuentaRepositorio.findById(Id);

        if (optional.isPresent()) {
            cuentaRepositorio.delete(optional.get());
        }

    }

    //DAR DE BAJA: necesito el alta para luego darlo de baja.Agregar esto a la entidad cuenta
    @Transactional
    public void darDeBaja(Long Id, Boolean Alta) throws Excepcion {
        Optional<Cuenta> respuesta = cuentaRepositorio.findById(Id);
        if (respuesta.isPresent()) {

            Cuenta cuenta = respuesta.get();

            cuenta.setAlta(false);
            cuentaRepositorio.save(cuenta);
        }

    }

    //MODIFICAR SALDO: Método para INGRESAR: sumar saldo + deposito
    @Transactional
    public void ingresarDinero(Double saldoActual, Double saldo, Double deposito, Long Id) throws Excepcion {
//        Deposito deposito = new deposito();
//        deposito.setFecha(New Date());
//       
        Optional<Cuenta> respuesta = cuentaRepositorio.findById(Id); //busco la cuenta, y si existe una con ese id la materializamos
        if (respuesta.isPresent()) {
            Cuenta cuenta = respuesta.get();
            cuenta.setSaldoActual(cuenta.getSaldo() + deposito);
            cuenta.setFecha(new Date());
            
            cuentaRepositorio.save(cuenta);

        }
    }

    //MODIFICAR SALDO: Método para RETIRAR dinero : saldoActual-extraccion (retiro/compra)
    @Transactional
    public void retirarDinero(Double saldoActual, Double saldo, Double extraccion, Long Id) throws Excepcion {
        //      Extraccion extraccion=new extraccion();
        //   extraccion.setFecha(New Date());
        Optional<Cuenta> respuesta = cuentaRepositorio.findById(Id);
        if (respuesta.isPresent()) {
            Cuenta cuenta = respuesta.get();
            cuenta.setSaldoActual(cuenta.getSaldo() - extraccion);
            cuenta.setFecha(new Date());

            cuentaRepositorio.save(cuenta);

        }
    }
}

//
//    public void validar(Long Id, Double Saldo, Double saldoActual, Boolean Alta, Double deposito, Double extraccion,
//            Usuario usuario) throws Exception {
//
//        if (Id == null || Id.toString().trim().isEmpty()) {
//            throw new Exception(" El Id no puede ser nulo");
//        }
//        if (Saldo < 0) {
//
//            throw new Exception(" Su cuenta esta vacia");
//        }
//        if (saldoActual < 0) {
//
//            throw new Exception(" Su cuenta esta vacia");
//        }
//
//        if (deposito < 0.00) {
//
//            throw new Exception(" Debe ingresar un monto superior a cero");
//        }
//
//        if (extraccion < 0.00) {
//
//            throw new Exception(" Debe extraer un monto superior a cero");
//        }
//
//        if (usuario == null) {
//            throw new Exception(" El usuario no puede ser nulo");
//        }
//
//    }
//
//    public void retirarDinero(Double saldoActual, Double saldo, Double saldo0, Long Id, Date fecha) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public void ingresarDinero(Double saldoActual, Double saldo, Long Id, Date fecha) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//}
//
////podria buscar la cuenta por su usuario y lueg realizar ciertas validaciones
