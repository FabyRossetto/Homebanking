package com.example.Homebanking.Repositorios;


import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
     @Query ("SELECT u from Usuario u WHERE u.apellido= apellido")
    public Usuario findByApellido (@Param ("apellido") String apellido);
    
     @Query ("SELECT u from Usuario u WHERE u.email= :email")
    public Usuario findByEmail (@Param ("email") String email);
    
   
//     @Query ("SELECT u from Usuario u WHERE u.cuenta.id= IdCuenta")
//    public Usuario findByCuenta (@Param ("IdCuenta") Long IdCuenta);
    
     @Query ("SELECT u from Usuario u WHERE u.DNI = DNI")
    public Usuario findByDNI (@Param ("DNI") String DNI);
}