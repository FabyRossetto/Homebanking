package com.example.Homebanking.Servicios;

import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Entidades.Transferencia;
import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Errores.Excepcion;
import com.example.Homebanking.Repositorios.CuentaRepositorio;
import com.example.Homebanking.Repositorios.TransferenciaRepositorio;
import com.example.Homebanking.Repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaServicio {

    @Autowired
    private CuentaRepositorio cuentaRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private TransferenciaRepositorio transferenciaRepositorio;

    //el error se debe a que no tengo creado los repo de usuario y transfernecia 
    //GUARDAR UNA CUENTA: CREACIÓN (necesito transferencia para crearlo?)
    @Transactional
    public Cuenta guardar(Long Id,Double saldo) throws Excepcion {
        
        Cuenta cuenta = new Cuenta();
        
        cuenta.setSaldo(saldo);
        cuenta.setAlta(Boolean.TRUE);
        cuenta.setFecha(new Date());
        cuenta.setId(Id);
       

        //PERSISTENCIA DEL OBJETO
        return cuentaRepositorio.save(cuenta);

}


    //ELIMINAR CUENTA
  
    public void borrarPorId(Long Id)  {
      
            cuentaRepositorio.deleteById(Id);
   
}

//
//    //DAR DE BAJA: necesito el alta para luego darlo de baja.Agregar esto a la entidad cuenta
    @Transactional
    public void darDeBaja(Long Id, Boolean Alta) throws Excepcion {
        Optional<Cuenta> respuesta = cuentaRepositorio.findById(Id);
        if (respuesta.isPresent()) {

            Cuenta cuenta = respuesta.get();

            cuenta.setAlta(false);
            cuentaRepositorio.save(cuenta);
        }

    }
}

//
//    //MODIFICAR SALDO: Método para INGRESAR: sumar saldo + deposito
//    @Transactional
//    public void ingresarDinero(Double saldoActual, Double saldo, Double deposito, Long Id, Date fecha) throws Excepcion {
////        Deposito deposito = new deposito();
////        deposito.setFecha(New Date());
//        Optional<Cuenta> respuesta = cuentaRepositorio.findById(Id); //busco la cuenta, y si existe una con ese id la materializamos
//        if (respuesta.isPresent()) {
//            Cuenta cuenta = respuesta.get();
//            cuenta.setSaldoActual(cuenta.getSaldo() + deposito);
//            cuenta.setFecha(fecha);
//
//            cuentaRepositorio.save(cuenta);
//
//        }
//    }
//
//    //MODIFICAR SALDO: Método para RETIRAR dinero : saldoActual-extraccion (retiro/compra)
//    @Transactional
//    public void retirarDinero(Double saldoActual, Double saldo, Double extraccion, Long Id, Date fecha,String IdUsuario) throws Excepcion {
//  //      Extraccion extraccion=new extraccion();
//    //   extraccion.setFecha(New Date());
//        Optional<Cuenta> respuesta = cuentaRepositorio.findById(Id);
//        if (respuesta.isPresent()) {
//            Cuenta cuenta = respuesta.get();
//            cuenta.setSaldoActual(cuenta.getSaldo() - extraccion);
//            cuenta.setFecha(fecha);
//
// 
//    
//
//            cuentaRepositorio.save(cuenta);
//
//        }
//    }
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
//}
//
////podria buscar la cuenta por su usuario y lueg realizar ciertas validaciones
