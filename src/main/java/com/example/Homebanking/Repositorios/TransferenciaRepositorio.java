package com.example.Homebanking.Repositorios;

import com.example.Homebanking.Entidades.Transferencia;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepositorio extends JpaRepository<Transferencia,Long> {
    
    /*@Query("SELECT tf FROM Transferencia tf WHERE tf.IdTransferencia =:IdTransferencia")
    public Transferencia buscarId(@Param("IdTransferencia") Long IdTransferencia);
    
    @Query("SELECT f FROM Transferencia f WHERE f.Fecha =:Fecha")
    public Transferencia buscarFecha(@Param("Fecha" Date Fecha));*/
}
