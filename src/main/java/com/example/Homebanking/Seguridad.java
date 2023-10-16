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

        http
                .authorizeRequests()
                .antMatchers("/usuario/*").hasRole("USUARIO")
                .antMatchers("/usuario/logincheck").permitAll()
                .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                .permitAll()
            .and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("clave")
                .defaultSuccessUrl("http://127.0.0.1:5500/Templates/index.html")
                .permitAll()
            .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
            .and().csrf().disable()
                .sessionManagement()
                .maximumSessions(1) 
                .maxSessionsPreventsLogin(true);
                

    }
}

/*
esta configuración estaba en el proyecto de recetapp pero lo dejé comentado
porque Fabi ya había usado la configuración de la encriptación de contraseña 
cuando se registra un usuario
@Autowired
    public Usuario usuarioServicio;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
    }*/

 /*
application properties
server.servlet.session.timeout: 300 
spring.security.sessions: if_required
 */
