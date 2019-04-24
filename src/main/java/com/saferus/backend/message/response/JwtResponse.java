/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.message.response;

import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author lucasbrito
 */
public class JwtResponse {
    
    private String token;
    private String type = "Bearer";
 
    public JwtResponse(String accessToken) {
        this.token = accessToken;
    }
 
    public String getAccessToken() {
        return token;
    }
 
    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }
 
    public String getTokenType() {
        return type;
    }
 
    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }
    
}
