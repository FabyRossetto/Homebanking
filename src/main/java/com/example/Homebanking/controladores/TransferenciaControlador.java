
package com.example.Homebanking.controladores;

import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Repositorios.CuentaRepositorio;
import com.example.Homebanking.Repositorios.TransferenciaRepositorio;
import com.example.Homebanking.Servicios.TransferenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/adminTransferencia")
public class TransferenciaControlador {
    
    @Autowired
    private TransferenciaRepositorio transferenciaRepositorio;
    
    @Autowired
    private TransferenciaServicio transferenciaServicio;
    
    @Autowired
    private Cuenta cuenta;
    
    @Autowired
    private CuentaRepositorio cuentaRepositorio;
    
    //vista con el form correpondiente para realizar la transferencia
    @GetMapping("/transferencia")
    public String crearTransferencia(ModelMap vistaTransferencia) {

        return "adminTransferencia";
    }
    
    @PostMapping("/realizarTransferencia")
    public String crearTransferencia(@RequestParam Long Id, Cuenta cuentaReceptora, double monto, ModelMap modelo) throws Exception {
        try {
            //transferenciaServicio.crearTransferencia(Id, cuentaReceptora, monto);
            modelo.put("Excelente", ("Transferencia realizada!"));
        } catch (Exception e) {
            e.getMessage();
            modelo.put("Error", "No se ha podido realizar la transferencia. Intente nuevamente más tarde. GRACIAS!");
        }
        return "adminTransferencia";
    }
    
}
