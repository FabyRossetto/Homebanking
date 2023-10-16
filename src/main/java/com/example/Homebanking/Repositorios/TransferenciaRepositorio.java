package com.example.Homebanking.Repositorios;

import com.example.Homebanking.Entidades.Transferencia;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepositorio extends JpaRepository<Transferencia,Long> {

    @Query ("SELECT t from Transferencia t WHERE t.monto = monto")
    public List<Transferencia> buscarTransferenciaXMonto(Double monto);

    @Query ("SELECT t from Transferencia t WHERE t.Fecha = fecha")
    public List<Transferencia> buscarTransferenciaXFecha(LocalDate fecha);


}
