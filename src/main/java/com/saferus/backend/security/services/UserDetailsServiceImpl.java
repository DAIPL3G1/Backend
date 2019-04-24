/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.security.services;

/**
 *
 * @author lucasbrito
 */

import com.saferus.backend.exceptions.DataNotFoundException;
import com.saferus.backend.model.Account;
import com.saferus.backend.model.User;
import com.saferus.backend.repository.AccountRepository;
import com.saferus.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    UserRepository userRepository;
 
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
      
        User user = userRepository.findOptionalUserByEmail(email)
                  .orElseThrow(() -> 
                        new UsernameNotFoundException("Utiliador não encontrado -> Email : " + email)
        );
 
        return UserPrinciple.build(user);
    }
}