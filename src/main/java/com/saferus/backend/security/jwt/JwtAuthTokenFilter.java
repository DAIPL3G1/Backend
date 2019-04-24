/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.security.jwt;

/**
 *
 * @author lucasbrito
 */

import com.saferus.backend.security.services.UserDetailsServiceImpl;
import java.io.IOException;
 
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
 
public class JwtAuthTokenFilter extends OncePerRequestFilter {
 
    @Autowired
    private JwtProvider tokenProvider;
 
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
 
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);
 
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                    HttpServletResponse response, 
                    FilterChain filterChain) 
                        throws ServletException, IOException {
        try {
          
            String jwt = getJwt(request);
            if (jwt!=null && tokenProvider.validateJwtToken(jwt)) {
                String username = tokenProvider.getUserNameFromJwtToken(jwt);
 
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication 
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
 
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Can NOT set user authentication -> Message: {}", e);
        }
 
        filterChain.doFilter(request, response);
    }
 
    private String getJwt(HttpServletRequest request) {
        
        if(!(request.getCookies() == null)){
            
            Cookie[] cookies = request.getCookies();
            
            for(Cookie cookie : cookies){
                if("token".equals(cookie.getName())){
                    System.out.println(cookie.getValue());
                    return cookie.getValue();
                }
            }
            
        }
        
        return null;
        /*String authHeader = request.getHeader("Authorization");
          
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
          return authHeader.replace("Bearer ","");
        }
 
        return null;*/
    }
}
