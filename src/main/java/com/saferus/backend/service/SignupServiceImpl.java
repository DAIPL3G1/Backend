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
import com.saferus.backend.model.AccountType;
import com.saferus.backend.model.User;
import com.saferus.backend.repository.AccountTypeRepository;
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
    private AccountTypeRepository atRepository;

    @Autowired
    private JavaMailSender sender;

    @Override
    public void signupUser(User newUser) {
        if(newUser.getPassword().isEmpty()){
            throw new BadRequestException("Por favor insira a palavra-passe");
        }
        else if(newUser.getEmail().isEmpty()){
            throw new BadRequestException("Por favor insira o email");
        }
        else if(newUser.getFirstname().isEmpty()){
            throw new BadRequestException("Por favor insira o nome");
        }
        else if(newUser.getLastname().isEmpty()){
            throw new BadRequestException("Por favor insira o apelido");
        }
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setEnabled(0);
        AccountType tp = atRepository.findAccountTypeById(1);
        if (tp == null) {
            throw new DataNotFoundException("Tipo de Conta não encontrado");
        }
        newUser.setAccountType(tp);
        if (userRepository.findUserByNif(newUser.getNif()) != null) {
            throw new DuplicatedException("Já existe um Utilizador com esse Nif");
        } else if (userRepository.findUserByEmail(newUser.getEmail()) != null) {
            throw new DuplicatedException("Já existe um Utilizador com esse Email");
        }
        userRepository.save(newUser);
    }

    @Override
    public void signupBroker(User newBroker) {
        if(newBroker.getPassword().isEmpty()){
            throw new BadRequestException("Por favor insira a palavra-passe");
        }
        else if(newBroker.getEmail().isEmpty()){
            throw new BadRequestException("Por favor insira o email");
        }
        else if(newBroker.getFirstname().isEmpty()){
            throw new BadRequestException("Por favor insira o nome");
        }
        else if(newBroker.getLastname().isEmpty()){
            throw new BadRequestException("Por favor insira o apelido");
        }
        newBroker.setPassword(bCryptPasswordEncoder.encode(newBroker.getPassword()));
        newBroker.setEnabled(0);
        AccountType tp = atRepository.findAccountTypeById(2);
        if (tp == null) {
            throw new DataNotFoundException("Tipo de Conta não Encontrado");
        }
        newBroker.setAccountType(tp);
        if (userRepository.findUserByNif(newBroker.getNif()) != null) {
            throw new DuplicatedException("Já existe um Mediador com esse Nif");
        } else if (userRepository.findUserByEmail(newBroker.getEmail()) != null) {
            throw new DuplicatedException("Já existe um Mediador com esse Email");
        }
        userRepository.save(newBroker);
    }

    @Override
    public void deleteUser(String user_nif) {
        User u = userRepository.findUserByNif(user_nif);
        if (u == null) {
            throw new DataNotFoundException("Utilizador não encontrado");
        }

        if (u.getAccountType().getId() == 1) {
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
        if (b.getAccountType().getId() == 2) {
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
