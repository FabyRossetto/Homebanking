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
                .antMatchers("/administrador/*").hasRole("ADMINISTRADOR")
                .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                .permitAll()
                .and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("DNI")
                .passwordParameter("clave")
                .defaultSuccessUrl("/index")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/principal.html") // Página de registro
                .loginProcessingUrl("/registro") // Ruta de procesamiento de registro
                .usernameParameter("dni")
                .passwordParameter("clave")
                .defaultSuccessUrl("/principal.hmtl#formulario")
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
