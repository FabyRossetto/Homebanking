package com.example.Homebanking.Servicios;

import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Entidades.Transferencia;
import com.example.Homebanking.Repositorios.TransferenciaRepositorio;
import com.example.Homebanking.Repositorios.UsuarioRepositorio;
import java.time.LocalDate;
import java.util.List;
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
    private NotificacionServicio notificacionServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private TransferenciaRepositorio transferenciaRepositorio;
    
    @Autowired
    private CuentaServicio cuentaServicio;

    @Transactional
    public Transferencia nvatf(String DNIEmisor, String DNIReceptor, Double monto) throws Exception {
        //String DNIEmisor,String DNIReceptor,
        try {
            validar(DNIEmisor, DNIReceptor, monto);

            Transferencia nuevatf = new Transferencia();
            //buscar DNI emisor y receptor a la transferencia
           nuevatf.setDNIEmisor(usuarioRepositorio.findByDNI(DNIEmisor).getDNI());
           nuevatf.setDNIReceptor(usuarioRepositorio.findByDNI(DNIReceptor).getDNI());
            
            //asignar cambios a las cuentas
           cuentaServicio.tfEx(usuarioRepositorio.findByDNI(DNIEmisor).getCuenta(), monto);
           cuentaServicio.tfDp(usuarioRepositorio.findByDNI(DNIReceptor).getCuenta(), monto);
            
            //asignar atributos a la transferencia
            nuevatf.setMonto(monto);
            nuevatf.setFecha(LocalDate.now());

            transferenciaRepositorio.save(nuevatf);

            //enviar(usuarioEmisor.getEmail());
            return nuevatf;
        } catch (Exception ex) {
            throw new Exception("Error al transferir");
        }
    }
    
    @Transactional
    public Transferencia nuevatf(Transferencia transferencia){
        return transferenciaRepositorio.save(transferencia);
    }

    private Cuenta cuentaSesion(String DNIEmisor) {

        if (!usuarioRepositorio.findByDNI(DNIEmisor).isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        
        Cuenta cuentaSesion = usuarioRepositorio.findByDNI(DNIEmisor).getCuenta();
        return cuentaSesion;
    }

    private void validar(String DNIEmisor, String DNIReceptor, Double monto) throws Exception {

        if (DNIEmisor.equalsIgnoreCase(DNIReceptor)) {
            throw new Exception("La cuenta "
                    + "emisora no puede ser la misma que la receptora");
        }
        if (DNIReceptor == null) {
            throw new Exception("Debe ingresar un DNI "
                    + "válido para realizar la tranferencia");
        }

        if (DNIReceptor.length() < 8) {
            throw new Exception("El DNI es inválido");
        }

        if (!DNIReceptor.equalsIgnoreCase(usuarioRepositorio.findByDNI(DNIReceptor).getDNI())) {
            throw new Exception("No se ha encontrado el usuario con dicho DNI");
        }

        if (monto < 1000) {
            throw new Exception("El monto de la transferencia debe ser mayor a $1000");
        }

        if (monto > usuarioRepositorio.findByDNI(DNIEmisor).getCuenta().getSaldoActual()) {
            throw new Exception("No tiene suficiente saldo para realizar la transferencia");
        }

    }

    public List<Transferencia> traerTodasTf() {
        List<Transferencia> tf = transferenciaRepositorio.findAll();
        return tf;
    }

    public List<Transferencia> buscarTransferenciaXMonto(Double monto) {
        List<Transferencia> listaTransferenciaXMonto = transferenciaRepositorio.buscarTransferenciaXMonto(monto);
        return listaTransferenciaXMonto;
    }

    public List<Transferencia> buscarTransferenciaXFecha(int anio, int mes, int dia) {
        LocalDate fecha = LocalDate.of(anio, mes, dia);
        List<Transferencia> listaTransferenciasXFecha = transferenciaRepositorio.buscarTransferenciaXFecha(fecha);
        return listaTransferenciasXFecha;
    }

    public void enviar(String email) throws Exception {
        notificacionServicio.enviar("HomebankingApp", "Estado de transferencia: Realizada con éxito!", email);
    }
    
}


