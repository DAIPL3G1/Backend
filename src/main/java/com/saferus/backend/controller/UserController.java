/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controller;

import com.saferus.backend.model.User;
import com.saferus.backend.model.Vehicle;
import com.saferus.backend.service.UserServiceImpl;
import java.util.List;
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
@RestController
public class UserController {
    
    @Autowired
    private UserServiceImpl userService;
    
    @RequestMapping(value = {"/readAllUsers"}, method = RequestMethod.GET)
    public List<User> readAllUsers(){
        return userService.readAllUsers();
    }
    
    @RequestMapping(value = {"/read/user/{user_nif}"}, method = RequestMethod.GET)
    public User readUser(@PathVariable("user_nif") String user_nif){
        return userService.readUser(user_nif);
    }
    
    @RequestMapping(value = {"/readAllBrokers"}, method = RequestMethod.GET)
    public List<User> readAllBrokers(){
        return userService.readAllBrokers();
    }
    
    @RequestMapping(value = {"/read/broker/{broker_nif}"}, method = RequestMethod.GET)
    public User readBroker(@PathVariable("broker_nif") String broker_nif){
        return userService.readBroker(broker_nif);
    }
    
    @RequestMapping(value = {"/update/user/{user_nif}"}, method = RequestMethod.PUT)
    public User updateInfo(@Valid @RequestBody User user, String user_nif){
        userService.updateInfo(user, user_nif);
        return user;
    }
    
    @RequestMapping(value = {"/update/password/{user_nif}"}, method = RequestMethod.PUT)
    public String updatePassword(@PathVariable("user_nif") String user_nif, @Valid @RequestBody String password, String newPassword){
        userService.updatePassword(user_nif, password, newPassword);
        return "Password alterada com sucesso";
    }
    
    @RequestMapping(value = {"/add/vehicle/{user_nif}"}, method = RequestMethod.POST)
    public String addVehicleToUser(@Valid @RequestBody Vehicle vehicle, @PathVariable("user_nif") String user_nif){
        userService.addVehicleToUser(vehicle, user_nif);
        return "Veiculo com a matricula {" + vehicle.getPlate() + "} adicionado com Sucesso";
    }
    
    
    
    
}