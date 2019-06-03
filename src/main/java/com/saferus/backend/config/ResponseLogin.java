/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.config;

import com.saferus.backend.model.User;
import java.io.Serializable;

/**
 *
 * @author lucasbrito
 */

//Model para receber a token e o Utilizador a que pertence
public class ResponseLogin implements Serializable{
    
    private String token;
    private User user;

    public ResponseLogin(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
}
