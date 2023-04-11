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
//    @Autowired
//    private UsuarioRepositorio usuarioRepositorio;
//
//    @Autowired
//    private TransferenciaRepositorio transferenciaRepositorio;

    //el error se debe a que no tengo creado los repo de usuario y transfernecia 
    //GUARDAR UNA CUENTA: CREACIÓN (necesito transferencia para crearlo?)
//    @Transactional
//    public Cuenta guardar(Long Id,Usuario usuario, Transferencia transferencia) throws Excepcion {
//
//        //SETEO DE ATRIBUTOS
//        Cuenta cuenta = new Cuenta();
//        cuenta.setUsuario(usuario);
//        cuenta.setTransferencia(transferencia);
//
//        //PERSISTENCIA DEL OBJETO
//        return cuentaRepositorio.save(cuenta);
//
//    }

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
    public void darDeBaja(Long Id,Date Alta) throws Excepcion {
        Optional<Cuenta> respuesta = cuentaRepositorio.findById(Id);
        if (respuesta.isPresent()) {

            Cuenta cuenta = respuesta.get();

            cuenta.setAlta(false);
            cuentaRepositorio.save(cuenta);
        }

    }

    //MODIFICAR SALDO
    @Transactional
    public void modificarSaldo(Double Saldo,Transferencia transferencia,Usuario usuario,Long Id){
        Optional<Cuenta> respuesta = cuentaRepositorio.findById(Id);
        if (respuesta.isPresent()) {

            Cuenta cuenta = respuesta.get();
            
    }
    }
}
