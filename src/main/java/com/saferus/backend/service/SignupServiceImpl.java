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

}
