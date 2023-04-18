package com.example.Homebanking.Repositorios;

import com.example.Homebanking.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
//     @Query ("SELECT c from Usuario WHERE c.apellido= apellido")
//    public Usuario buscarPorApellido (@Param ("apellido") String apellido);
     @Query ("SELECT c from Usuario c WHERE c.email= :email")
    public Usuario buscarPorMail (@Param ("email") String Email);
//     @Query ("SELECT c from Usuario WHERE c.cuenta= cuenta")
//    public Usuario buscarPorCuenta (@Param ("cuenta") int cuenta);
//     @Query ("SELECT c from Usuario WHERE c.fechaAlta= fechaAlta")
//    public Usuario buscarfechaAlta (@Param ("fechaAlta") int fechaAlta);
}