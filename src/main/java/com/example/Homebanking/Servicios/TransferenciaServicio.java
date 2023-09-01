package com.example.Homebanking.Servicios;

import com.example.Homebanking.Entidades.Transferencia;
import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Repositorios.TransferenciaRepositorio;
import com.example.Homebanking.Repositorios.UsuarioRepositorio;
import java.util.LocalDate;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author SabriMallea
 */
@Service
public class TransferenciaServicio {


=======
    @Autowired
    private TransferenciaServicio transferenciaServicio;
    
    @Autowired
    private Transferencia tf;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private TransferenciaRepositorio transferenciaRepositorio;

    @Transactional
    public Transferencia tf(double monto, Usuario usuarioEmisor, String DNIReceptor) throws Exception {
        try {
            validar(monto, usuarioEmisor, DNIReceptor);
            //Transferencia tf = new Transferencia();
            tf.setMonto(monto);
            tf.setCuentaEmisora(usuarioEmisor.getCuenta());
            tf.setCuentaReceptora(usuarioRepositorio.findByDNI(DNIReceptor).getCuenta());
            tf.setFecha(LocalDate.now());

            transferenciaRepositorio.save(tf);

            return tf;
        } catch (Exception ex) {
            throw new Exception("Error al transferir");
        }
    }

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
}
