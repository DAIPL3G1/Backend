/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

/**
 *
 * @author lucasbrito
 */
import com.saferus.backend.model.User;
import com.saferus.backend.model.VerificationToken;
import com.saferus.backend.repository.UserRepository;
import com.saferus.backend.repository.VerificationTokenRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

@Service
public class VerificationTokenServiceImpl {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    
    @Autowired
    private final SignupService sendingMailService;

    @Autowired
    public VerificationTokenServiceImpl(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, SignupService sendingMailService){
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.sendingMailService = sendingMailService;
    }

    public String createVerification(String email, String pw) throws MessagingException, AddressException, IOException{
        List<User> users = userRepository.findByEmail(email);
        User user;
        if (users.isEmpty()) {
            user = new User();
            user.setEmail(email);
            user.setPassword(pw);
            userRepository.save(user);
        } else {
            user = users.get(0);
        }

        List<VerificationToken> verificationTokens = verificationTokenRepository.findByUserEmail(email);
        VerificationToken verificationToken;
        if (verificationTokens.isEmpty()) {
            verificationToken = new VerificationToken();
            verificationToken.setUser(user);
            verificationTokenRepository.save(verificationToken);
        } else {
            verificationToken = verificationTokens.get(0);
        }

        sendingMailService.sendEmail(email, pw, verificationToken.getToken());
        return verificationToken.getToken();
    }

    public ResponseEntity<String> verifyEmail(String token){
        List<VerificationToken> verificationTokens = verificationTokenRepository.findByToken(token);
        if (verificationTokens.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token.");
        }

        VerificationToken verificationToken = verificationTokens.get(0);
        if (verificationToken.getExpiredDateTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.unprocessableEntity().body("Expired token.");
        }

        verificationToken.setConfirmedDateTime(LocalDateTime.now());
        verificationToken.setStatus(VerificationToken.STATUS_VERIFIED);
        verificationToken.getUser().setEnabled(1);
        verificationTokenRepository.save(verificationToken);

        return ResponseEntity.ok("You have successfully verified your email address.");
    }
}