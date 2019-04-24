/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controller;

/**
 *
 * @author lucasbrito
 */
import com.saferus.backend.message.request.LoginForm;
import com.saferus.backend.message.request.SignupForm;
import com.saferus.backend.message.response.JwtResponse;
import com.saferus.backend.model.Role;
import com.saferus.backend.model.RoleName;
import com.saferus.backend.model.User;
import com.saferus.backend.repository.RoleRepository;
import com.saferus.backend.repository.UserRepository;
import com.saferus.backend.security.jwt.JwtProvider;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthRestAPIs {

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
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
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
}
