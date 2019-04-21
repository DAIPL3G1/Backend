/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomAuthenticationProvider implements AuthenticationProvider {

@Override
public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
    String username = authentication.getName();
    String password = (String) authentication.getCredentials();
    List<GrantedAuthority> grantedAuths = new ArrayList<>();


       //validate and do your additionl logic and set the role type after your validation. in this code i am simply adding admin role type
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN" ));



    return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
}

@Override
public boolean supports(Class<?> arg0) {
    return true;
}

} 