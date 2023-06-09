/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Servicios;

import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Entidades.Transferencia;
import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Repositorios.CuentaRepositorio;
import com.example.Homebanking.Repositorios.TransferenciaRepositorio;
import com.example.Homebanking.Repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author SabriMallea
 */
@Service
public class TransferenciaServicio {

   @Autowired
    Transferencia transferencia;

    @Autowired
    TransferenciaRepositorio transferenciaRepositorio;

    @Autowired
    Cuenta cuenta;
    
    @Autowired
    CuentaRepositorio cuentaRepositorio;
    
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void crearTransferencia(Long Id, double monto, String DNI) throws Exception {

        /*try {
            validar(Id, monto, DNI);

            Transferencia nuevaTransferencia = new Transferencia();
            transferencia.setId(Id);
            //buscar cuenta vinculada al usuario
            Optional<Usuario> usuario = usuarioRepositorio.findByDNI(DNI);
        Usuario usuario= usuario.get();
        if(usuario.getCuenta()==null){
         //mensaje de error
        } else{

            transferencia.setCuentaReceptora(usuario.getCuenta());
            
            transferencia.setFecha(new Date());
            transferencia.setMonto(monto);
            transferenciaRepositorio.save(nuevaTransferencia);
        } 
        
        }catch (Exception ex) {
            throw new Exception("Error al transferir");
        }*/

    }

    //lo pongo en private porque sólo voy a usar este método dentro del servicio
    private void validar(Long Id, double monto, String DNI) throws Exception {
        //si ingresa el campo vacío de cuentaEmisora
       
        //si ingresa el campo vacío de cuentaReceptora
        /*if (cuentaReceptora == null) {
            throw new Exception("No ha ingresado una cuenta receptora");
        }*/
        //si cuentaEmisora es igual a cuentaReceptora
        
        //que el monto sea mayor al disponible en la cuenta y al mínimo, y que no exceda el máximo
        //fijar monto minimo y máximo para la transferencia
        
        if(DNI.length() < 8){
            throw new Exception ("El DNI es inválido");
        }
        
        if (monto < cuenta.getSaldo() || monto < 1000 || monto > 300000) {
            throw new Exception("El monto de la transferencia no es correcto");
        }

        //si la cuenta emisora es la misma que la receptora
        //si la cuenta emisora existe
        //si la cuenta recptora existe
        //validación de fecha?? se crea de manera automática
    }

   /*public List<Transferencia> buscarTransferenciaXMonto(Double monto) {
        List<Transferencia> listaTransferenciaXMonto = transferenciaRepositorio.buscarTransferenciaXMonto(monto);
        return listaTransferenciaXMonto;
    }

    /*public List<Transferencia> buscarTransferenciaXFecha(int dia, int mes, int anio) {
        Date fecha = new Date(dia, mes, anio);
        List<Transferencia> listaTransferenciasXFecha = transferenciaRepositorio.buscarTransferenciaXFecha(fecha);
        return listaTransferenciasXFecha;
    }

    public List<Transferencia> buscarTransferenciaXFecha(Date fecha){
        List <Transferencia> listaTransferenciasXFecha = transferenciaRepositorio.buscarTransferenciaXFecha(fecha);
        return listaTransferenciasXFecha;
    }*/
}
