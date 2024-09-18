package com.example.Homebanking.Repositorios;

import com.example.Homebanking.Entidades.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepositorio extends JpaRepository<Cuenta, Long> {

    //buscar cuenta por Id y por usuario
    @Query("SELECT c FROM Cuenta c WHERE c.idCuenta = :idCuenta")
    public Cuenta buscarPorId(@Param("idCuenta") Long idCuenta);

}
