/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.config;

/**
 *
 * @author lucasbrito
 */
import com.saferus.backend.security.jwt.JwtAuthEntryPoint;
import com.saferus.backend.security.jwt.JwtAuthTokenFilter;
import com.saferus.backend.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/authenticated").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/emails/{email}/{generated_password}").permitAll()
                .antMatchers("/emails/verify_email/{token}").permitAll()
                .antMatchers("/request/bind/{broker_nif}/{user_nif}").hasAuthority("USER")
                .antMatchers("/readAllUsers").permitAll()
                .antMatchers("/readAllBrokers").permitAll()
                .antMatchers("/readAllBinds").permitAll()
                .antMatchers("/read/all/clients/{broker_nif}").permitAll()
                .antMatchers("/bind/request/pending/{broker_nif}").permitAll()
                .antMatchers("/read/bound/vehicles/{broker_nif}").permitAll()
                .antMatchers("/read/user/{user_nif}").hasAnyAuthority("USER")
                .antMatchers("/read/broker/{broker_nif}").hasAuthority("BROKER")
                .antMatchers("/read/bind/{bind_id}").permitAll()
                .antMatchers("/delete/user/{user_nif}").hasAuthority("USER")
                .antMatchers("/delete/broker/{broker_nif}").hasAuthority("BROKER")
                .antMatchers("/delete/vehicle/{vehicle_id}").hasAnyAuthority("USER")
                .antMatchers("/unbind/{user_nif}").hasAuthority("USER")
                .antMatchers("/validate/user/{user_nif}").hasAuthority("ADMIN")
                .antMatchers("/validate/broker/{broker_nif}").hasAuthority("ADMIN")
                .antMatchers("/validate/bind/{bind_id}").hasAuthority("BROKER")
                .antMatchers("/update/user/{user_nif}").hasAuthority("USER")
                .antMatchers("/update/password/{user_nif}").hasAuthority("USER")
                .antMatchers("/update/bind/{bind_id}").hasAnyRole("USER", "BROKER")
                .antMatchers("/add/vehicle/{user_nif}").hasAnyAuthority("USER")
                .anyRequest().authenticated();
        
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    
}
