/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controller;

import com.saferus.backend.exceptions.AppException;
import com.saferus.backend.model.Role;
import com.saferus.backend.model.RoleName;
import com.saferus.backend.model.User;
import com.saferus.backend.payload.ApiResponse;
import com.saferus.backend.payload.JwtAuthenticationResponse;
import com.saferus.backend.payload.LoginRequest;
import com.saferus.backend.payload.SignUpRequest;
import com.saferus.backend.repository.RoleRepository;
import com.saferus.backend.repository.UserRepository;
import com.saferus.backend.security.CurrentUser;
import com.saferus.backend.security.JwtTokenProvider;
import com.saferus.backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        User user = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        String jwt = tokenProvider.generateToken(authentication);

        Cookie cookie = new Cookie("SaferusCookie", jwt);
        cookie.setPath("/a85679/daipl3g1/DAIPL3G1.github.io/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(5*60);
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, user));
    }

    @PostMapping("/signup/user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByNif(signUpRequest.getNif())) {
            return new ResponseEntity(new ApiResponse(false, "Nif já em uso"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email já em uso!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getNif(), signUpRequest.getFirstname(), signUpRequest.getLastname(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("Não existe este tipo de Utilizador."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Utilizador Registado com sucesso"));
    }

    @PostMapping("/signup/broker")
    public ResponseEntity<?> registerBroker(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByNif(signUpRequest.getNif())) {
            return new ResponseEntity(new ApiResponse(false, "Nif já em uso"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email já em uso"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getNif(), signUpRequest.getFirstname(), signUpRequest.getLastname(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_BROKER)
                .orElseThrow(() -> new AppException("Não existe tipo de Mediador"));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/brokers/{email}")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Mediador registado com Sucesso!"));
    }

    @GetMapping("/authenticated")
    @Secured({"ROLE_USER", "ROLE_BROKER", "ROLE_ADMIN"})
    public User getCurrentUser() {
        return userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    
    @Secured({"ROLE_USER", "ROLE_BROKER", "ROLE_ADMIN"})
    @GetMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies.length == 0){
            throw new AppException("Não há cookies existentes :D");
        }
        for(Cookie cookie: cookies){
            if(cookie.getName().equals("SaferusCookie")){
                cookie.setPath("/");
                cookie.setValue("");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "Logout feito com Sucesso!"));
        
    }
}
