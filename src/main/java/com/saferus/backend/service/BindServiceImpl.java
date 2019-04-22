/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.model.User;
import com.saferus.backend.model.Vehicle;
import com.saferus.backend.model.Bind;
import com.saferus.backend.model.ValidateBind;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.saferus.backend.repository.UserRepository;
import com.saferus.backend.repository.VehicleRepository;
import com.saferus.backend.repository.BindRepository;
import com.saferus.backend.repository.VehicleTypeRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author lucasbrito
 */
@Service("bindService")
public class BindServiceImpl implements BindService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BindRepository bindRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleTypeRepository vtRepository;

    @Override
    public void requestBind(String plate, String broker_nif, String user_nif) {
        Bind newBind = new Bind();
        newBind.setBroker(userRepository.findUserByNif(broker_nif));
        newBind.setUser(userRepository.findUserByNif(user_nif));
        Vehicle v = vehicleRepository.findVehicleByPlate(plate);
        newBind.setVehicle(v);
        newBind.setRequestDate(Instant.now());
        newBind.setEnabled(0);
        bindRepository.save(newBind);
    }

    @Override
    public Bind validateBind(ValidateBind vb, int bind_id) throws Exception {
        ZoneId denverTimeZone = ZoneId.of("Europe/Lisbon");
        Bind b = bindRepository.findBindById(bind_id);
        b.setStartDate(ZonedDateTime.now().toInstant());
        b.setEndDate(ZonedDateTime.now().plusYears(1).toInstant());
        Vehicle v = b.getVehicle();
        v.setVehicleType(vtRepository.findVehicleTypeById(2));
        vehicleRepository.save(v);
        if (b.getEnabled() == 0) {
            b.setEnabled(1);
        } else {
            throw new Exception("Bind already activated!");
        }
        b.setContractCode(vb.getContract_code());
        bindRepository.save(b);
        return b;
    }

    @Override
    public void unbind(String user_nif) throws Exception {
        User u = userRepository.findUserByNif(user_nif);
        Bind b = bindRepository.findBindByUser(u);
        if (b != null) {
            b.setEnabled(0);
            Vehicle v = b.getVehicle();
            v.setVehicleType(vtRepository.findVehicleTypeById(1));
            vehicleRepository.save(v);
            bindRepository.save(b);
        } else {
            throw new Exception("Error occured to unbind");
        }
    }

    @Override
    public List<Bind> readBinds() {
        return bindRepository.findAll();
    }

    @Override
    public Bind readBind(int bind_id) {
        Bind b = bindRepository.findBindById(bind_id);
        return b;
    }

    @Override
    public Bind updateBind(int bind_id, Bind bind) {
        bind.setId(bind_id);
        return bindRepository.save(bind);
    }
    
    public List<Bind> readAllPendingBind(String broker_nif){
        List<Bind> binds = new ArrayList<>();
        for(Bind b : bindRepository.findAll()){
            if(b.getBroker().getNif().equals(broker_nif)){
                if(b.getEnabled() == 0){
                    binds.add(b);
                }
            }
        }
        return binds;
    }
    
    @Override
    public void unbindVehicle(String plate) throws Exception{
        Vehicle v = vehicleRepository.findVehicleByPlate(plate);
        Bind b = bindRepository.findBindByUser(v.getUser());
        if (b != null) {
            b.setEnabled(0);
            v.setVehicleType(vtRepository.findVehicleTypeById(1));
            vehicleRepository.save(v);
            bindRepository.save(b);
        } else {
            throw new Exception("Error occured to unbind");
        }
    }

}
