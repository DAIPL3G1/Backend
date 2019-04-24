/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controller;

import com.saferus.backend.model.Account;
import com.saferus.backend.service.SignupServiceImpl;
import com.saferus.backend.service.VerificationTokenServiceImpl;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lucasbrito
 */
@RestController
public class SignupController {

    @Autowired
    private SignupServiceImpl signupService;

    @Autowired
    VerificationTokenServiceImpl verificationTokenService;

    @RequestMapping(value = {"/access_denied"}, method = RequestMethod.GET)
    public String accessDenied() {
        return "Acesso Negado! Tente Novamente mais tarde!";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/delete/user/{user_nif}"}, method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("user_nif") String user_nif) {
        signupService.deleteUser(user_nif);
    }

    @Secured({"ROLE_BROKER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/delete/broker/{broker_nif}"}, method = RequestMethod.DELETE)
    public void deleteBroker(@PathVariable("broker_nif") String broker_nif) {
        signupService.deleteBroker(broker_nif);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/validate/user/{user_nif}"}, method = RequestMethod.PUT)
    public String validateUser(@PathVariable("user_nif") String user_nif) throws Exception {
        return signupService.validateUser(user_nif);
    }

    @Secured({"ROLE_BROKER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/validate/broker/{broker_nif}"}, method = RequestMethod.PUT)
    public String validateBroker(@PathVariable("broker_nif") String broker_nif) throws Exception {
        return signupService.validateBroker(broker_nif);
    }

    @RequestMapping(value = {"/emails"}, method = RequestMethod.POST)
    public String sendEmail(@Valid @RequestBody Account account) throws MessagingException, AddressException, IOException {
        return verificationTokenService.createVerification(account);
    }

    @RequestMapping(value = {"/emails/verify_email/{token}"}, method = RequestMethod.GET)
    public String verifyEmail(@PathVariable("token") String token) throws MalformedURLException {
        return verificationTokenService.verifyEmail(token).getBody();
    }
}
