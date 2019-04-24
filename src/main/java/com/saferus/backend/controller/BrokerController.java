/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controller;

import com.saferus.backend.model.Bind;
import com.saferus.backend.model.User;
import com.saferus.backend.model.Vehicle;
import com.saferus.backend.service.BindServiceImpl;
import com.saferus.backend.service.UserServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lucasbrito
 */
@RestController
public class BrokerController {

    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private BindServiceImpl bindService;

    @Secured({"ROLE_BROKER", "ROLE_ADMIN"})
    @RequestMapping(value = "/read/all/clients/{broker_nif}", method = RequestMethod.GET)
    public List<User> readAllClientsFromABroker(@PathVariable("broker_nif") String broker_nif) {
        return userService.readAllClientsFromBroker(broker_nif);
    }

    @Secured({"ROLE_BROKER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/read/bound/vehicles/{broker_nif}"}, method = RequestMethod.GET)
    public List<Vehicle> readAllBoundVehicles(@PathVariable("broker_nif") String broker_nif) {
        return userService.readAllBoundVehicles(broker_nif);
    }

    @Secured({"ROLE_BROKER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/bind/request/pending/{broker_nif}"}, method = RequestMethod.GET)
    public List<Bind> readAllPendingBind(@PathVariable("broker_nif") String broker_nif){
        return bindService.readAllPendingBind(broker_nif);
    }

}
