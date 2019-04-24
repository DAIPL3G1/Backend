/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.config;

import com.saferus.backend.security.CustomUserDetailsService;
import com.saferus.backend.security.JwtAuthenticationEntryPoint;
import com.saferus.backend.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                    .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                        .permitAll()
                    .antMatchers("/**")
                        .permitAll()
                    .antMatchers("/checkEmailAvailability")
                        .permitAll()
                    .antMatchers(HttpMethod.GET, "/**", "/**")
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                    .and()
                        .logout()
                            .deleteCookies("SaferusCookie")
                            .invalidateHttpSession(true);

        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}

/*
.antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup/user").permitAll()
                .antMatchers("/signup/broker").permitAll()
                .antMatchers("/trip/read/{vehicle_id}").permitAll()
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
*/