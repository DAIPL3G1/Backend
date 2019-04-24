/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controller;

import com.saferus.backend.message.request.LoginForm;
import com.saferus.backend.message.request.SignupForm;
import com.saferus.backend.message.response.JwtResponse;
import com.saferus.backend.model.Role;
import com.saferus.backend.model.RoleName;
import com.saferus.backend.model.User;
import com.saferus.backend.repository.RoleRepository;
import com.saferus.backend.repository.UserRepository;
import com.saferus.backend.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.saferus.backend.service.UserService;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author lucasbrito
 */
@RestController
@CrossOrigin
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        
        Cookie cookie = new Cookie("JSESSIONID", jwt);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(500000);
        response.addCookie(cookie);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignupForm signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Já existe um Utilizador com esse Email!",
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByNif(signUpRequest.getNif())) {
            return new ResponseEntity<String>("Já existe um Utilizador com esse Nif",
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getNif(), signUpRequest.getFirstame(),
                signUpRequest.getLastname(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "ADMIN":
                    Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Tipo de Utilizador não encontrado"));
                    roles.add(adminRole);

                    break;
                case "BROKER":
                    Role pmRole = roleRepository.findByName(RoleName.BROKER)
                            .orElseThrow(() -> new RuntimeException("Tipo de Utilizador não encontrado"));
                    roles.add(pmRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.USER)
                            .orElseThrow(() -> new RuntimeException("Tipo de Utilizador não encontrado"));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok().body("Utilizador Registado com sucesso");
    }

    @Autowired
    private UserService userService;
    
    @Autowired
    Environment environment;    

    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    public User getCurrentUser(HttpServletRequest request){
        return userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public boolean HomeMessage() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    @GetMapping("/test")
    String testConnection() throws UnknownHostException {
        return "Your server is up and running at port: " + environment.getProperty("local.server.port") + " " + InetAddress.getLocalHost().getHostAddress() + " " + InetAddress.getLocalHost().getHostName(); 
    }

    @GetMapping("/protected")
    ResponseEntity hello(){
        return new ResponseEntity( "hello", HttpStatus.OK);
    }
    
}
