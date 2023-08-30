package com.example.Homebanking.Servicios;

import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Entidades.Transferencia;
import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Repositorios.CuentaRepositorio;
import com.example.Homebanking.Repositorios.TransferenciaRepositorio;
import com.example.Homebanking.Repositorios.UsuarioRepositorio;
import java.util.Date;
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
    Transferencia transferencia;

    @Autowired
    TransferenciaRepositorio transferenciaRepositorio;

    @Autowired
    Cuenta cuenta;

    @Autowired
    Usuario usuario;

    @Autowired
    CuentaRepositorio cuentaRepositorio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    /*@Transactional
    public Transferencia crearTransferencia(double monto,
            Usuario usuarioEmisor, String DNIReceptor) throws Exception {

        try {
            validar(monto, usuarioEmisor, DNIReceptor);
            Transferencia tf= new Transferencia();
            tf.setCuentaEmisora(usuario.getCuenta());
            tf.setCuentaReceptora(usuarioRepositorio.findByDNI(DNIReceptor).getCuenta());
            tf.setFecha(new Date());
            tf.setMonto(monto);
        } catch (Exception ex) {
            throw new Exception("Ha ocurrido un error.");
        }
        return transferenciaRepositorio.save(this.transferencia);
    }

    /*@Transactional
    public void crearTransferencia(Long Id, double monto, String DNI) throws Exception {
        
        try {
            validar(Id, monto, DNI);

            Transferencia nuevaTransferencia = new Transferencia();
            transferencia.setId(Id);
            //buscar cuenta vinculada al usuario
            Usuario usuario = usuarioRepositorio.findByDNI(DNI);
            if (usuario.isPresent()) {
            transferencia.setCuentaReceptora(usuario.getCuenta());
            transferencia.setFecha(new Date());
            transferencia.setMonto(monto);
            transferenciaRepositorio.save(nuevaTransferencia);
        }
        }catch (Exception ex) {
            throw new Exception("Error al transferir");
        }

    }*/
    //lo pongo en private porque sólo voy a usar este método dentro del servicio
    private void validar(double monto, Usuario usuarioEmisor, String DNIReceptor) throws Exception {

        if (DNIReceptor == null) {
            throw new Exception("Debe ingresar un DNI "
                    + "válido para realizar la tranferencia");
        }

        if (DNIReceptor.length() < 8) {
            throw new Exception("El DNI es inválido");
        }

        if (DNIReceptor.equalsIgnoreCase(usuarioEmisor.getDNI())) {
            throw new Exception("No puede transferir a su misma cuenta");
        }

        if (!DNIReceptor.equalsIgnoreCase(usuarioRepositorio.findByDNI(DNIReceptor).getDNI())) {
            throw new Exception("No se ha encontrado el usuario con dicho DNI");
        }

        if (monto < usuarioEmisor.getCuenta().getSaldo() || monto < 1000 || monto > 300000) {
            throw new Exception("El monto de la transferencia no es correcto");
        }

    }
    
    /*public List<Transferencia> buscarTransferenciaXReceptor(String DNIReceptor){
        return null;
    }
    cómo hago para generar la consulta a la base de datos para que me traiga la 
    lista de transferencias según el DNI del usuario
    */

    /*public List<Transferencia> buscarTransferenciaXMonto(Double monto) {
        List<Transferencia> listaTransferenciaXMonto = transferenciaRepositorio.buscarTransferenciaXMonto(monto);
        return listaTransferenciaXMonto;
    }

    public List<Transferencia> buscarTransferenciaXFecha(int dia, int mes, int anio) {
        Date fecha = new Date(dia, mes, anio);
        List<Transferencia> listaTransferenciasXFecha = transferenciaRepositorio.buscarTransferenciaXFecha(fecha);
        return listaTransferenciasXFecha;
    }

    /*public List<Transferencia> buscarTransferenciaXFecha(Date fecha){
        List <Transferencia> listaTransferenciasXFecha = transferenciaRepositorio.buscarTransferenciaXFecha(fecha);
        return listaTransferenciasXFecha;
    }*/
}
