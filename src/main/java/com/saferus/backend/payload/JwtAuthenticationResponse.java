/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.payload;

import com.saferus.backend.model.User;

public class JwtAuthenticationResponse {
    
    private String accessToken;
    private String tokenType = "Bearer";
    private User userlogged;

    public JwtAuthenticationResponse(String accessToken, User userlogged) {
        this.accessToken = accessToken;
        this.userlogged = userlogged;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public User getUserlogged() {
        return userlogged;
    }

    public void setUserlogged(User userlogged) {
        this.userlogged = userlogged;
    }
    
    
}