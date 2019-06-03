/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.config;

import com.saferus.backend.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableConfigurationProperties
public class AccessConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/signup/user").permitAll()
                .antMatchers("/signup/broker").permitAll()
                .antMatchers("/forgetPassword").permitAll()
                .antMatchers("/trip/read/{vehicle_id}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/trip/read/datas/{vehicle_id}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/trip/tratment").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/emails").permitAll()
                .antMatchers("/emails/verify_email/{token}").permitAll()
                .antMatchers("/authenticated").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/auth").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/protected").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/test").permitAll()
                .antMatchers("/request/bind/{broker_nif}/{user_nif}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/readAllUsers").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/readAllBrokers").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/readAllBinds").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/readAllVehicles").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/read/all/clients/{broker_nif}").hasAnyAuthority("BROKER", "ADMIN")
                .antMatchers("/bind/request/pending/{broker_nif}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/read/bound/vehicles/{broker_nif}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/read/user/{user_nif}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/read/broker/{broker_nif}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/read/bind/{bind_id}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/read/user/vehicles/{user_nif}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/delete/user/{user_nif}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/delete/broker/{broker_nif}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/delete/vehicle/{vehicle_id}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/unbind/user/{user_nif}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/unbind/vehicle/{vehicle_id}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/validate/user/{user_nif}").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/validate/broker/{broker_nif}").hasAnyAuthority("BROKER", "ADMIN")
                .antMatchers("/validate/bind/{bind_id}").hasAnyAuthority("BROKER", "ADMIN")
                .antMatchers("/unvalidate/bind/{bind_id}").hasAnyAuthority("BROKER", "ADMIN")
                .antMatchers("/update/user/{user_nif}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/update/password/{user_nif}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/update/bind/{bind_id}").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/add/vehicle/{user_nif}").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }
}
