/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Homebanking.Servicios;

import org.springframework.stereotype.Service;


/**
 *
 * @author SabriMallea
 */
@Service
public class TransferenciaServicio {

//   @Autowired
//    Transferencia transferencia;
//
//    @Autowired
//    TransferenciaRepositorio transferenciaRepositorio;
//
//    @Autowired
//    com.example.Homebanking.Entidades.Cuenta cuenta;
//    
//    @Autowired
//    CuentaRepositorio cuentaRepositorio;
//
//    @Transactional
//    public void crearTransferencia(Long Id, Cuenta cuentaReceptora, double monto) throws Exception {
//
//        try {
//            validar(Id, cuentaReceptora, monto);
//
//            Transferencia nuevaTransferencia = new Transferencia();
//            transferencia.setId(Id);
//            transferencia.setCuentaReceptora(cuentaReceptora);
//            transferencia.setFecha(new Date());
//            transferencia.setMonto(monto);
//            transferenciaRepositorio.save(nuevaTransferencia);
//        } catch (Exception ex) {
//            throw new Exception("Error al transferir");
//        }
//
//    }
//
//    //lo pongo en private porque sólo voy a usar este método dentro del servicio
//    private void validar(Long Id, Cuenta cuentaReceptora, double monto) throws Exception {
//        //si ingresa el campo vacío de cuentaEmisora
//       
//        //si ingresa el campo vacío de cuentaReceptora
//        if (cuentaReceptora == null) {
//            throw new Exception("No ha ingresado una cuenta receptora");
//        }
//        //si cuentaEmisora es igual a cuentaReceptora
//        
//        //que el monto sea mayor al disponible en la cuenta y al mínimo, y que no exceda el máximo
//        //fijar monto minimo y máximo para la transferencia
//        if (monto < cuenta.getSaldo() || monto < 1000 || monto > 300000) {
//            throw new Exception("El monto de la transferencia no es correcto");
//        }

        //si la cuenta emisora es la misma que la receptora
        //si la cuenta emisora existe
        //si la cuenta recptora existe
        //validación de fecha?? se crea de manera automática
//    }

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
