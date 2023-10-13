package com.example.Homebanking;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Seguridad extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http
               
                .authorizeRequests()
                .antMatchers("/usuario/*").hasRole("USUARIO")
                
                .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                .permitAll()
            .and().formLogin()
                .loginPage("http://127.0.0.1:5500/login.html")
                .loginProcessingUrl("http://localhost:8080/usuario/logincheck")
                .usernameParameter("email")
                .passwordParameter("clave")
                .defaultSuccessUrl("http://127.0.0.1:5500/perfil.html?id={id}")
                .permitAll()
            .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("http://127.0.0.1:5500/login.html")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
            .and().csrf().disable()
                .sessionManagement()
                .maximumSessions(1) 
                .maxSessionsPreventsLogin(true);
                

    }
    
}
