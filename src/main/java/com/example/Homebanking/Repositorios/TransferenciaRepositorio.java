package com.example.Homebanking.Repositorios;

import com.example.Homebanking.Entidades.Transferencia;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepositorio extends JpaRepository<Transferencia,Long> {
    
   @Query("SELECT tf FROM Transferencia tf WHERE tf.Id =:Id")
    public Transferencia buscarId(@Param("Id") Long IdTransferencia);

    @Query("SELECT f FROM Transferencia f WHERE f.Fecha =:Fecha")
    public List<Transferencia> buscarTransferenciaXFecha(@Param("Fecha") Date fecha);

    /*@Query("SELECT m FROM Transferencia m WHERE m.Monto =:Monto")
    public List<Transferencia> buscarTransferenciaXMonto(@Param("monto") Double monto);
    la que no anda es la de búqueda por monto
    cuando busco transferencia por fecha o monto no me va a devolver una única transferencia
    sino que puede ser más de una, por lo que debería devolver una lista de transferencias en el caso de
    buscar por fecha y por monto
    */
}
