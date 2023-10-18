/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Homebanking.controladores;

import com.example.Homebanking.Entidades.Transferencia;
import com.example.Homebanking.Repositorios.TransferenciaRepositorio;
import com.example.Homebanking.Servicios.TransferenciaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author SabriMallea
 */
@RestController
@RequestMapping("/transferencia")
public class TransferenciaControlador {

    @Autowired
    private TransferenciaServicio tfServicio;

    @Autowired
    private TransferenciaRepositorio tfRepositorio;

    @GetMapping()
    public List<Transferencia> listaTransferencias() {
        return tfRepositorio.findAll();
    }

    @PostMapping()
    public ResponseEntity<Transferencia> nuevaTf(@RequestBody Transferencia tran) throws Exception {
        
    return ResponseEntity.status(200).body(tfServicio.nuevatf(tran));
    }
    
}
