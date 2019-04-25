/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controller;

import com.saferus.backend.model.User;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.saferus.backend.service.UserService;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author lucasbrito
 */
@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @ResponseBody
    public String HomeMessage(Principal p) {
        User u = userService.findUserByEmail(p.getName());
        String nome = u.getFirstname() + " " + u.getLastname();
        return "Loggado com Sucesso Sr." + nome;
    }

    @Autowired
    Environment environment;

    @GetMapping("/test")
    String testConnection() throws UnknownHostException {
        return "Your server is up and running at port: " + environment.getProperty("local.server.port") + " " + InetAddress.getLocalHost().getHostAddress() + " " + InetAddress.getLocalHost().getHostName(); 
    }

}
