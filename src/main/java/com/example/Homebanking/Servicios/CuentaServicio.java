package com.example.Homebanking.Servicios;

import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Entidades.Transferencia;
import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Errores.Excepcion;
import com.example.Homebanking.Repositorios.CuentaRepositorio;
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
    public Cuenta guardar(Long Id, Usuario usuario, Transferencia transferencia) throws Excepcion {

        //SETEO DE ATRIBUTOS
        Cuenta cuenta = new Cuenta();
        cuenta.setUsuario(usuario);
        cuenta.setSaldo(Double.NaN);
        //cuenta.setTransferencia(transferencia);

        //PERSISTENCIA DEL OBJETO
        return cuentaRepositorio.save(cuenta);

    }

    //ELIMINAR CUENTA
    @Transactional
    public void borrarPorId(Long Id) throws Excepcion {
        Optional<Cuenta> optional = cuentaRepositorio.findById(Id);

        if (optional.isPresent()) {
            cuentaRepositorio.delete(optional.get());
        }

    }

    //DAR DE BAJA: necesito el alta para luego darlo de baja.Agregar esto a la entidad cuenta
    @Transactional
    public void darDeBaja(Long Id, Date Alta) throws Excepcion {
        Optional<Cuenta> respuesta = cuentaRepositorio.findById(Id);
        if (respuesta.isPresent()) {

            Cuenta cuenta = respuesta.get();

            cuenta.setAlta(false);
            cuentaRepositorio.save(cuenta);
        }

    }

    //MODIFICAR SALDO: Método para INGRESAR: sumar saldo + ingreso
    @Transactional
    public void ingresarDinero(Double saldoActual,Double saldo,Double ingreso, Long Id) throws Excepcion {
//        Deposito deposito = new deposito();
//        deposito.setFecha(New Date());
        Optional<Cuenta> respuesta = cuentaRepositorio.findById(Id); //busco la cuenta, y si existe una con ese id la materializamos
        if (respuesta.isPresent()) {
            Cuenta cuenta = respuesta.get();
            cuenta.setSaldoActual(cuenta.getSaldo()+ingreso);
            
            cuentaRepositorio.save(cuenta);
            

        }
    }
    //MODIFICAR SALDO: Método para RETIRAR dinero : saldoActual-retiro
    @Transactional
    public void retirarDinero(Double saldoActual,Double saldo,Double retiro, Long Id) throws Excepcion {
//        Extraccion extraccion=new extraccion();
//        extraccion.setFecha(New Date());
        Optional<Cuenta> respuesta = cuentaRepositorio.findById(Id);
        if (respuesta.isPresent()) {
            Cuenta cuenta = respuesta.get();
            cuenta.setSaldoActual(cuenta.getSaldo()-retiro);
            
            cuentaRepositorio.save(cuenta);
 
        }
    }
}

//podria buscar la cuenta por su usuario y lueg realizar ciertas validaciones