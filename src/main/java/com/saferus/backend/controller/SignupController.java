/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controller;

import com.saferus.backend.model.User;
import com.saferus.backend.service.SignupServiceImpl;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lucasbrito
 */
@RestController()
public class SignupController {

    @Autowired
    private SignupServiceImpl signupService;

    @RequestMapping(value = {"/signup/user"}, method = RequestMethod.POST)
    public User signupUser(@Valid @RequestBody User user) throws Exception {
        User result = signupService.findGenericUserByNif(user.getNif());
        if (result != null) {
            throw new Exception("User already exists!");
        }
        signupService.signupUser(user);
        return user;
    }

    @RequestMapping(value = {"/signup/broker"}, method = RequestMethod.POST)
    public User signupBroker(@Valid @RequestBody User b) {
        signupService.signupBroker(b);
        return b;
    }

    @RequestMapping(value = {"/access_denied"}, method = RequestMethod.GET)
    public String accessDenied() {
        return "Acesso Negado! Tente Novamente mais tarde!";
    }

    @RequestMapping(value = {"/delete/user/{user_nif}"}, method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("user_nif") String user_nif) {
        signupService.deleteUser(user_nif);
    }

    @RequestMapping(value = {"/delete/broker/{broker_nif}"}, method = RequestMethod.DELETE)
    public void deleteBroker(@PathVariable("broker_nif") String broker_nif) {
        signupService.deleteBroker(broker_nif);
    }

    @RequestMapping(value = {"/validate/user/{user_nif}"}, method = RequestMethod.PUT)
    public String validateUser(@PathVariable("user_nif") String user_nif) throws Exception {
        return signupService.validateUser(user_nif);
    }

    @RequestMapping(value = {"/validate/broker/{broker_nif}"}, method = RequestMethod.PUT)
    public String validateBroker(@PathVariable("broker_nif") String broker_nif) throws Exception {
        return signupService.validateBroker(broker_nif);
    }
}
