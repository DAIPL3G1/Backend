/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.configuracao;

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
public class ConfiguracaoAcesso extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    private final String USERS_QUERY = "select email, password, ativo from conta where email=?";
    private final String ROLES_QUERY = "select c.email, tc.designacao from conta c inner join conta_tipo_conta ctc on (c.id = ctc.conta_id) inner join tipo_conta tc on (ctc.tipoconta_id=tc.id) where c.email=?";
    
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
                .antMatchers("/registo/utilizador").permitAll()
                .antMatchers("/registo/mediador").permitAll()
                .antMatchers("/eliminar/utilizador/{utilizador_id}").hasAuthority("GENERICO")
                .antMatchers("/eliminar/mediador/{mediador_id}").hasAuthority("MEDIADOR")
                .antMatchers("/validar/utilizador/{utilizador_id}").hasAuthority("ADMIN")
                .antMatchers("/validar/mediador/{mediador_id}").hasAuthority("ADMIN")
                .antMatchers("/consultar").permitAll()
                .antMatchers("/consultar/{utilizador_id}").hasAuthority("GENERICO")
                .antMatchers("/consultar/segurados").permitAll()
                .antMatchers("/consultar/segurado/{segurado_id}").hasAuthority("SEGURADO")
                .antMatchers("/consultar/mediadores").permitAll()
                .antMatchers("/consultar/mediador/{mediador_id}").hasAuthority("MEDIADOR")
                .antMatchers("/alterar/{utilizador_id}").hasAuthority("GENERICO")
                .antMatchers("/alterar/password/{utilizador_id}").hasAuthority("GENERICO")
                .antMatchers("/vinculacao/{mediador_id}/{utilizador_id}").hasAuthority("GENERICO")
                .antMatchers("/vinculacao/validar/{vinculacao_id}").hasAuthority("MEDIADOR")
                .antMatchers("/vinculacao/eliminar/{segurado_id}").hasAuthority("SEGURADO")
                .antMatchers("/vinculacao/consultar").permitAll()
                .antMatchers("/vinculacao/consultar/{segurado_id}").hasAuthority("SEGURADO")
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
                .and().exceptionHandling().accessDeniedPage("/accesso_negado");
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
