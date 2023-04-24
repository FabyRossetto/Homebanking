package com.example.Homebanking.Controladores;

import com.example.Homebanking.Repositorios.CuentaRepositorio;
import com.example.Homebanking.Repositorios.TarjetaRepositorio;
import com.example.Homebanking.Servicios.CuentaServicio;
import com.example.Homebanking.Servicios.TarjetaServicio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/administracionCuenta")
public class CuentaControlador {

    @Autowired
    private CuentaRepositorio cuentaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private TarjetaRepositorio tarjetaRepositorio;

//    @Autowired
//    private TransferenciaRepositorio transferenciaRepositorio;
//    
    @Autowired
    private CuentaServicio cuentaservicio;

    @Autowired
    private UsuarioServicio usuarioservicio;

    @Autowired
    TarjetaServicio tarjetaservicio;

    @GetMapping("/guardarCuenta")
    public String guardarCuenta(ModelMap vista) {

        return "administracionCuenta"; //me devuelve la vista
    }
    
     @PostMapping("/guardarCuenta")
    public String guardarEditorial(@RequestParam Usuario usuario,Double saldo, Date fecha,Boolean alta, ModelMap modelo) throws Exception {
        try {
            cuentaservicio.guardar(Long.MIN_VALUE, usuario,saldo,fecha,alta);
            modelo.put("Gracias por elegirnos", ("Cuenta dada de alta con éxito"));
        } catch (Exception e) {
            e.getMessage();
            modelo.put("Error", "La cuenta no ha podido ser dada de alta");
        }
        return "administracionCuenta";
    }

}
//alta?