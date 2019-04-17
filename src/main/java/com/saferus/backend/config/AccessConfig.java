/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author lucasbrito
 */
@Configuration
@EnableWebSecurity
public class AccessConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    private final String USERS_QUERY = "select email, password, enabled from account where email=?";
    private final String ROLES_QUERY = "select a.email, t.name from account a, account_type t where (a.account_type_id = t.id) and a.email = ?";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(USERS_QUERY)
                .authoritiesByUsernameQuery(ROLES_QUERY)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup/user").permitAll()
                .antMatchers("/signup/broker").permitAll()
                .antMatchers("/test").permitAll()
                .antMatchers("/bind/{broker_nif}/{user_nif}").hasAuthority("USER")
                .antMatchers("/readAllUsers").permitAll()
                .antMatchers("/readAllBrokers").permitAll()
                .antMatchers("/readAllBinds").permitAll()
                .antMatchers("/read/user/{user_nif}").hasAuthority("USER")
                .antMatchers("/read/broker/{broker_nif}").hasAuthority("BROKER")
                .antMatchers("/read/bind/{bind_id}").permitAll()
                .antMatchers("/delete/user/{user_nif}").hasAuthority("USER")
                .antMatchers("/delete/broker/{broker_nif}").hasAuthority("BROKER")
                .antMatchers("/unbind/{user_nif}").hasAuthority("USER")
                .antMatchers("/validate/user/{user_nif}").hasAuthority("ADMIN")
                .antMatchers("/validate/broker/{broker_nif}").hasAuthority("ADMIN")
                .antMatchers("/validate/bind/{bind_id}").hasAuthority("BROKER")
                .antMatchers("/update/{user_nif}").hasAuthority("USER")
                .antMatchers("/update/password/{user_nif}").hasAuthority("USER")
                .antMatchers("/update/bind/{bind_id}").hasAnyAuthority("USER")
                .antMatchers("/add/vehicle/{user_nif}").hasAnyAuthority("USER")
                .antMatchers("/dashboard/").hasAuthority("ADMIN").anyRequest()
                .authenticated().and().csrf().disable()
                .formLogin().loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/home")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60 * 60)
                .and().exceptionHandling().accessDeniedPage("/access_denied");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);

        return db;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}