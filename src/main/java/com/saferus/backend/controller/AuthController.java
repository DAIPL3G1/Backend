/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controller;

import com.saferus.backend.config.LoginForm;
import com.saferus.backend.config.ResponseLogin;
import com.saferus.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.saferus.backend.service.UserService;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author lucasbrito
 */
@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    Environment environment;    

    
    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    @ResponseStatus(value=HttpStatus.OK)
    public User Authenticate(final HttpServletRequest request) throws UnsupportedEncodingException{
        return userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseStatus(value=HttpStatus.OK)
    public ResponseLogin Login(HttpServletResponse response, @Valid @RequestBody LoginForm login){
        String token = userService.authenticateUser(login.getEmail(), login.getPassword(), response);
        User u = userService.findUserByEmail(login.getEmail());
        return new ResponseLogin(token, u);
    }
    
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public boolean HomeMessage() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    @GetMapping("/test")
    String testConnection() throws UnknownHostException {
        return "Your server is up and running at port: " + environment.getProperty("local.server.port") + " " + InetAddress.getLocalHost().getHostAddress() + " " + InetAddress.getLocalHost().getHostName(); 
    }
    
}
