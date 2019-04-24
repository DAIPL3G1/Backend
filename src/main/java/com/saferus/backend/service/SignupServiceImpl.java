/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.exceptions.BadRequestException;
import com.saferus.backend.exceptions.DataNotFoundException;
import com.saferus.backend.exceptions.DuplicatedException;
import com.saferus.backend.exceptions.GenericException;
import com.saferus.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.saferus.backend.repository.UserRepository;
import java.io.IOException;
import java.net.URL;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 *
 * @author lucasbrito
 */
@Service("signupService")
public class SignupServiceImpl implements SignupService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender sender;

    @Override
    public void deleteUser(String user_nif) {
        User u = userRepository.findUserByNif(user_nif);
        if (u == null) {
            throw new DataNotFoundException("Utilizador não encontrado");
        }

        if (u.getRoles().equals("USER")) {
            userRepository.delete(u);
        } else {
            throw new GenericException("Não foi possível eliminar o Utilizador");
        }
    }

    @Override
    public void deleteBroker(String broker_nif) {
        User b = userRepository.findUserByNif(broker_nif);
        if (b == null) {
            throw new DataNotFoundException("Mediador não encontrado");
        }
        if (b.getRoles().equals("BROKER")) {
            userRepository.delete(b);
        } else {
            throw new GenericException("Não foi possível eliminar o Mediador");
        }
    }

    @Override
    public String validateUser(String user_nif) throws Exception {
        User u = userRepository.findUserByNif(user_nif);
        if (u == null) {
            throw new DataNotFoundException("Utilizador não encontrado");
        }
        if (u.getEnabled() == 0) {
            u.setEnabled(1);
            userRepository.save(u);
        } else {
            throw new GenericException("Utilizador já foi ativado");
        }
        return "Utilizador validado com Sucesso";
    }

    @Override
    public String validateBroker(String broker_nif) throws Exception {
        User b = userRepository.findUserByNif(broker_nif);
        if (b == null) {
            throw new DataNotFoundException("Mediador não encontrado");
        }
        if (b.getEnabled() == 0) {
            b.setEnabled(1);
            userRepository.save(b);
        } else {
            throw new GenericException("Mediador já foi ativado");
        }
        return "Mediador validado com Sucesso";
    }

    @Override
    public User findUserByNif(String user_nif) {
        if (userRepository.findUserByNif(user_nif) == null) {
            throw new DataNotFoundException("Utilizador não encontrado");
        }
        return userRepository.findUserByNif(user_nif);
    }

    @Override
    public String sendEmail(String email, String pw, String verificationCode) {
        try {
            User user = userRepository.findUserByEmail(email);
            URL url = new URL("https:/saferusbackend.herokuapp.com/emails/verify_email/" + verificationCode);

            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(user.getEmail());
            helper.setSubject("Safe'R'Us - Bem-Vindo " + user.getFirstname() + " - Ative a sua conta e altere a password para prosseguir!");
            helper.setText("Bem-Vindo " + user.getFirstname() + "\nPassword gerada: " + pw + "\nAtive a sua conta: " + url);
            sender.send(message);
        } catch (MessagingException | IOException | DataNotFoundException ex) {
            throw new GenericException(ex.getMessage());
        }
        return "Email enviado com sucesso";
    }

}
