/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controller;

import com.saferus.backend.model.Bind;
import com.saferus.backend.model.ValidateBind;
import com.saferus.backend.model.Vehicle;
import com.saferus.backend.service.BindServiceImpl;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
public class BindController {
    
    @Autowired
    private BindServiceImpl bindService;
    
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/request/bind/{broker_nif}/{user_nif}"}, method = RequestMethod.POST)
    public String requestBind(@Valid @RequestBody Vehicle vehicle, @PathVariable("broker_nif") String broker_nif, @PathVariable("user_nif") String user_nif) throws Exception{
        bindService.requestBind(vehicle.getPlate(), broker_nif, user_nif);
        return "Pedido de Vinculacao feito com Sucesso";
    }
    
    @Secured({"ROLE_BROKER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/validate/bind/{bind_id}"}, method = RequestMethod.PUT)
    public String validateBind(@Valid @RequestBody ValidateBind vb, String contract_code, @PathVariable("bind_id") int bind_id) throws Exception{
        bindService.validateBind(vb, bind_id);
        return "Vinculacao Validada Com Sucesso";
    }
    
    @Secured({"ROLE_BROKER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/unvalidate/bind/{bind_id}"}, method = RequestMethod.PUT)
    public String unValidateBind(@PathVariable("bind_id") int bind_id) throws Exception{
        bindService.unvalidateBind(bind_id);
        return "Vinculacao Não Validada Com Sucesso";
    }
    
    @Secured({"ROLE_USER", "ROLE_BROKER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/unbind/user/{user_nif}"}, method = RequestMethod.PUT)
    public String unbind(@PathVariable("user_nif") String user_nif) throws Exception{
        bindService.unbind(user_nif);
        return "Utilizador desvinculado com Sucesso";
    }
    
    @Secured({"ROLE_USER", "ROLE_BROKER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/unbind/vehicle/{vehicle_id}"}, method = RequestMethod.PUT)
    public void unbindVehicle(@PathVariable("vehicle_id") int vehicle_id) throws Exception{
        bindService.unbindVehicle(vehicle_id);
    }
    
    @Secured({"ROLE_USER", "ROLE_BROKER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/readAllBinds"}, method = RequestMethod.GET)
    public List<Bind> readAllBind(){
        return bindService.readBinds();
    }
    
    @Secured({"ROLE_USER", "ROLE_BROKER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/read/bind/{bind_id}"}, method = RequestMethod.GET)
    public Bind readBind(@PathVariable("bind_id") int bind_id){
        return bindService.readBind(bind_id);
    }
    
    @Secured({"ROLE_USER", "ROLE_BROKER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/update/bind/{bind_id}"}, method = RequestMethod.PUT)
    public Bind updateBind(@PathVariable("bind_id") int bind_id, @Valid @RequestBody Bind bind){
        return bindService.updateBind(bind_id, bind);
    }
    
}
