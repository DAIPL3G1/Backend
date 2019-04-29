/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.model.Account;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author lucasbrito
 */
@Service("verificationTokenService")
public interface VerificationTokenService {
    
    public String createVerification(Account account) throws MessagingException, AddressException, IOException;
    public ResponseEntity<String> verifyEmail(String token);


    
}
