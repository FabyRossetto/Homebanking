
package com.example.Homebanking.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificacionServicio {
    
    @Autowired
    private JavaMailSender mailsender;
    
    @Async
    public void enviar(String cuerpo,String titulo,String mail){
        SimpleMailMessage message= new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom("englishgate.ga@gmail.com");//cambiar
        message.setSubject(titulo);
        message.setText(cuerpo);
        
        mailsender.send(message);
    }
    
}  
//Hay que llamar al servicio cuando uno se registra, desde usuario, al final del metodo notificacionServicio.enviar()
//luego de qué acciones deberia notificarse al usuario? pensar eso: luego de realizar una transferencia, un deposito, compra, extraer dinero no olvidarme
//vincularlo a la clase como un atributo