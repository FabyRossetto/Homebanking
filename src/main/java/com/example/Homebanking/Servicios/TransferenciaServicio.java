/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Servicios;

import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Entidades.Transferencia;
import com.example.Homebanking.Repositorios.CuentaRepositorio;
import com.example.Homebanking.Repositorios.TransferenciaRepositorio;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author SabriMallea
 */
public class TransferenciaServicio {

    @Autowired
    Transferencia transferencia;

    @Autowired
    Cuenta cuenta;

    @Autowired
    TransferenciaRepositorio transferenciaRepositorio;

    @Autowired
    CuentaRepositorio cuentaRepositorio;

    @Transactional
    public void crearTransferencia(Long Id, Cuenta cuentaEmisora, Cuenta cuentaReceptora, Date fecha, double monto) throws Exception {

        try {
            validar(Id, cuentaEmisora, cuentaReceptora, monto);

            Transferencia nuevaTransferencia = new Transferencia();
            transferencia.setId(Id);
            transferencia.setCuentaEmisora(cuentaEmisora);
            transferencia.setCuentaReceptora(cuentaReceptora);
            transferencia.setFecha(new Date());
            transferencia.setMonto(monto);
        } catch (Exception ex) {
            throw new Exception("Error al transferir");
        }
      
    }

    //lo pongo en private porque sólo voy a usar este método dentro del servicio
    private void validar(Long Id, Cuenta cuentaEmisora, Cuenta cuentaReceptora, double monto) throws Exception {
        //si ingresa el campo vacío de cuentaEmisora
        if (cuentaEmisora == null) {
            throw new Exception("No ha ingresado una cuenta emisora");
        }
        //si ingresa el campo vacío de cuentaReceptora
        if (cuentaReceptora == null) {
            throw new Exception("No ha ingresado una cuenta receptora");
        }
        //si cuentaEmisora es igual a cuentaReceptora
        if (cuentaEmisora.equals(cuentaReceptora)) {
            throw new Exception("La cuenta emisora no puede ser igual a la cuenta receptora");
        }
        //que el monto sea mayor al disponible en la cuenta y al mínimo
        if (monto < cuentaEmisora.getSaldo()) {
            throw new Exception("No cuenta con los fondos suficientes para realizar la transacción");
        }

        //si la cuenta emisora es la misma que la receptora
        //si la cuenta emisora existe
        //si la cuenta recptora existe
        //validación de fecha?? se crea de manera automática
    }
    
    public void buscarTransferenciaXMonto(Double monto){
     //llamr al repositorio que debe devover una lista de trasnferencias
    }
    
}
