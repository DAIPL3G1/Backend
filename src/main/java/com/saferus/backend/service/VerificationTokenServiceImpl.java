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
import com.saferus.backend.model.Account;
import com.saferus.backend.model.User;
import com.saferus.backend.model.VerificationToken;
import com.saferus.backend.repository.UserRepository;
import com.saferus.backend.repository.VerificationTokenRepository;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

    public String createVerification(Account account) throws MessagingException, AddressException, IOException{
        if(account == null){
     //       throw new DataNotFoundException("Conta não encontrada");
        }
        List<User> users = userRepository.findByEmail(account.getEmail());
        User user;
        if (users.isEmpty()) {
            user = new User();
            user.setEmail(account.getEmail());
            user.setPassword(account.getPassword());
            userRepository.save(user);
        } else {
            user = users.get(0);
        }

        List<VerificationToken> verificationTokens = verificationTokenRepository.findByUserEmail(account.getEmail());
        VerificationToken verificationToken;
        if (verificationTokens.isEmpty()) {
            verificationToken = new VerificationToken();
            verificationToken.setUser(user);
            verificationTokenRepository.save(verificationToken);
        } else {
            verificationToken = verificationTokens.get(0);
        }

        sendingMailService.sendEmail(account.getEmail(), account.getPassword(), verificationToken.getToken());
        return verificationToken.getToken();
    }

    public ResponseEntity<String> verifyEmail(String token) throws MalformedURLException{
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
        verificationToken.getUser().setEnabled(true);
        verificationTokenRepository.save(verificationToken);
        
        URL page = new URL("https://saferus.herokuapp.com");

        return ResponseEntity.ok("Ativaste o teu email com sucesso. \nClica aqui para voltares à Página Inicial e fazeres Login: " + page);
    }
}