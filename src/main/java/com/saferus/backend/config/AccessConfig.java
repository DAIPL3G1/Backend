/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.config;

/*import javax.annotation.Resource;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author lucasbrito
 */
 /*@Configuration
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

        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/authenticated").hasAnyAuthority("USER", "BROKER", "ADMIN")
                .antMatchers("/auth").hasRole("USER")
                .antMatchers("/protected").hasRole("USER")
                .antMatchers("/signup/user").permitAll()
                .antMatchers("/signup/broker").permitAll()
                .antMatchers("/emails/{email}/{generated_password}").permitAll()
                .antMatchers("/emails/verify_email/{token}").permitAll()
                .antMatchers("/test").permitAll()
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
                .and()
                .formLogin()
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/authenticated")
                    .failureUrl("/login?error=true")
                    .usernameParameter("email")
                    .passwordParameter("password")
                .and().logout()
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/logout")
                .permitAll();

    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*")
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);

        return db;
    }
    
    
}*/
import com.saferus.backend.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableConfigurationProperties
public class AccessConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().sessionManagement().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }
    
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*")
                .allowedHeaders("*");
    }
    
    

}
