/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Servicios;

import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Entidades.Transferencia;
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
    TransferenciaRepositorio transferenciaRepositorio;

    @Transactional
    public Transferencia nuevaTransferencia(Long Id, Cuenta cuentaEmisora, Cuenta cuentaReceptora, Date fecha, double monto) throws Exception {
        //validar();
        transferencia.setId(Id);
        transferencia.setCuentaEmisora(cuentaEmisora);
        transferencia.setCuentaReceptora(cuentaReceptora);
        transferencia.setFecha(new Date());
        //transferencia.setMonto(monto);
        
        //nuevaTransferencia
        return null;
    }
    
     public void validar(Long Id, Cuenta cuentaEmisora, Cuenta cuentaReceptora, Date fecha, double monto) throws Exception {

      //si la cuenta emisora es la misma que la receptora
      //si la cuenta emisora existe
      //si la cuenta recptora existe
      //validación de fecha?? se crea de manera automática

    }
}
