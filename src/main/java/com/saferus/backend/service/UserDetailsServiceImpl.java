/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.repository.UserRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

//MUDAR PARa JWT

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
    
  @Autowired
  private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      com.saferus.backend.model.User user = repository.findUserByEmail(username);

    if(user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    
    if(user.getType().equals("USER")){
        authorities.add(new SimpleGrantedAuthority("USER"));
    }
    else if(user.getType().equals("BROKER")){
        authorities.add(new SimpleGrantedAuthority("BROKER"));
    }
    else if(user.getType().equals("ADMIN")){
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
    }
    
    boolean enabled = false;
    if(user.getEnabled() == 0){
        enabled = false;
    }
    else{
        enabled = true;
    }

    return new User(user.getEmail(), user.getPassword(), enabled, true, true, true, authorities);
  }
}