/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.model.AccountType;
import com.saferus.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.saferus.backend.repository.AccountTypeRepository;
import com.saferus.backend.repository.UserRepository;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setEnabled(0);
        AccountType tp = atRepository.findAccountTypeById(1);
        newUser.setAccountType(tp);
        userRepository.save(newUser);
    }

    @Override
    public void signupBroker(User newBroker) {
        newBroker.setPassword(bCryptPasswordEncoder.encode(newBroker.getPassword()));
        newBroker.setEnabled(0);
        AccountType tp = atRepository.findAccountTypeById(2);
        newBroker.setAccountType(tp);
        userRepository.save(newBroker);
    }

    @Override
    public void deleteUser(String user_nif) {
        User u = userRepository.findUserByNif(user_nif);

        if ((userRepository.findUserByNif(user_nif) != null) && u.getType().equals("USER")) {
            userRepository.delete(u);
        }
    }

    @Override
    public void deleteBroker(String broker_nif) {
        User b = userRepository.findUserByNif(broker_nif);
        if ((userRepository.findUserByNif(broker_nif) != null) && b.getType().equals("BROKER")) {
            userRepository.delete(b);
        }
    }

    @Override
    public String validateUser(String user_nif) throws Exception {
        User u = userRepository.findUserByNif(user_nif);
        if (u.getEnabled() == 0) {
            u.setEnabled(1);
            userRepository.save(u);
        } else {
            throw new Exception("User has been already actived");
        }
        return "Utilizador validado com Sucesso";
    }

    @Override
    public String validateBroker(String broker_nif) throws Exception {
        User m = userRepository.findUserByNif(broker_nif);
        if (m.getEnabled() == 0) {
            m.setEnabled(1);
            userRepository.save(m);
        } else {
            throw new Exception("Broker has been already activated");
        }
        return "Mediador validado com Sucesso";
    }

    @Override
    public User findGenericUserByNif(String user_nif) {
        return userRepository.findUserByNif(user_nif);
    }

    @Override
    public String sendEmail(String email, String pw, String verificationCode) throws AddressException, MessagingException, IOException {
        User user = userRepository.findUserByEmail(email);
        URL url = new URL("http://3-dot-protean-genius-233018.appspot.com/emails/verify_email/" + verificationCode);
        
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(user.getEmail());
        helper.setSubject("Safe'R'Us - Bem-Vindo " + user.getFirstname() + " - Ative a sua conta e altere a password para prosseguir!");
        helper.setText("Bem-Vindo " + user.getFirstname() + "\nPassword gerada: " + pw + "\nAtive a sua conta: " + url);
        sender.send(message);

        /*
        
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("jlucasbrito1@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        msg.setSubject("Safe'R'Us - Bem-Vindo " + user.getFirstname() + " - Ative a sua conta e altere a password para prosseguir!");
        msg.setContent("Safe'R'Us", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Bem-Vindo " + user.getFirstname() + "\nPassword gerada: " + pw + "\nAltere a sua password aqui: ", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        msg.setContent(multipart);
        Transport.send(msg);
         */
        return "Email sent successfuly";
    }

}
