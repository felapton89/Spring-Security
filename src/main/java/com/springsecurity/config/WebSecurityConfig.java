package com.springsecurity.config;

import com.springsecurity.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    String[] resources = new String[]{
            "/include/**", "/css/**", "/img/**", "/icons/**", "/js/**", "/layer/**"
    };

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    //CONFIGURO LA AUTENTICACION
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    //CONFIGURO LA PETICION HTTP
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(resources).permitAll()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/admin*").access("hasRole('ADMIN')")
                .antMatchers("/user*").access("hasRole('USER') or hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/menu")
                .failureUrl("login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/login?logout");
    }

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(4); //Rango entre 4-31
    }



}
