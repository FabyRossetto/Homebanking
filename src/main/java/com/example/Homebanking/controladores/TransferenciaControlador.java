
package com.example.Homebanking.controladores;

=======
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.example.Homebanking.Entidades.Cuenta;
import com.example.Homebanking.Entidades.Usuario;
import com.example.Homebanking.Repositorios.CuentaRepositorio;
import com.example.Homebanking.Repositorios.TransferenciaRepositorio;
import com.example.Homebanking.Servicios.TransferenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/adminTransferencia")
public class TransferenciaControlador {
    

=======
    @Autowired
    private TransferenciaServicio transferenciaServicio;
    
    
    //vista con el form correpondiente para realizar la transferencia
    @GetMapping("/transferencia")
    public String crearTransferencia(ModelMap vistaTf) {

        return "adminTransferencia";
    }
    
    @PostMapping("/realizarTransferencia")
    public String crearTransferencia(@RequestParam double monto, @AuthenticationPrincipal Usuario usuarioEmisor, String DNIReceptor, ModelMap modelo) throws Exception {
        try {
            transferenciaServicio.tf(0, usuarioEmisor, DNIReceptor);
            modelo.put("Excelente", ("Transferencia realizada!"));
        } catch (Exception e) {
            e.getMessage();
            modelo.put("Error", "No se ha podido realizar la transferencia. Intente nuevamente más tarde. GRACIAS!");
        }
        return "adminTransferencia";
    }
    
    @GetMapping("/listaTf")
    public String listarTodasLasRecetas(ModelMap modelMap) {
        modelMap.put("todas","Transferencias");
        modelMap.addAttribute("Id", transferenciaServicio.traerTodasTf());
        return "listaTransferencias";
    }
    

}
