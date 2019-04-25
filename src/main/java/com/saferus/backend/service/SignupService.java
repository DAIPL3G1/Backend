/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.model.User;

/**
 *
 * @author lucasbrito
 */
public interface SignupService {
    
    public User findGenericUserByNif(String nif);
    public void signupUser(User newUser);
    public void signupBroker(User newBroker);
    public void deleteUser(String user_nif);
    public void deleteBroker(String broker_nif);
    public String validateUser(String user_nif) throws Exception;
    public String validateBroker(String broker_nif) throws Exception;
    
}
