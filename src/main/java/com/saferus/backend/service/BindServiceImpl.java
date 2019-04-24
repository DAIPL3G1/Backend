/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.exceptions.AppException;
import com.saferus.backend.exceptions.BadRequestException;
import com.saferus.backend.model.User;
import com.saferus.backend.model.Vehicle;
import com.saferus.backend.model.Bind;
import com.saferus.backend.model.ValidateBind;
import com.saferus.backend.model.VehicleType;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.saferus.backend.repository.UserRepository;
import com.saferus.backend.repository.VehicleRepository;
import com.saferus.backend.repository.BindRepository;
import com.saferus.backend.repository.VehicleTypeRepository;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

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
        User broker = userRepository.findUserByNif(broker_nif);
        User user = userRepository.findUserByNif(user_nif);
        Vehicle vehicle = vehicleRepository.findVehicleByPlate(plate);
        if(broker == null) {
            throw new AppException("Mediador não encontrado");
        } else if (user == null) {
            throw new AppException("Utilizador não encontrado");
        } else if (vehicle == null) {
            throw new AppException("Veículo não encontrado");
        }
        newBind.setBroker(broker);
        newBind.setUser(user);
        newBind.setVehicle(vehicle);
        newBind.setRequestDate(Instant.now());
        newBind.setRequest(1);
        newBind.setAccepted(0);
        if (newBind.equals(bindRepository.findBindByVehicle(vehicle))) {
            throw new BadRequestException("Já existe um Vinculo com este veiculo");
        }
        bindRepository.save(newBind);
    }

    @Override
    public Bind validateBind(ValidateBind vb, int bind_id) throws Exception {
        ZoneId denverTimeZone = ZoneId.of("Europe/Lisbon");
        Bind b = bindRepository.findBindById(bind_id);
        if (b == null) {
            throw new AppException("Vinculo não encontrado");
        }
        b.setStartDate(ZonedDateTime.now(denverTimeZone).toInstant());
        b.setEndDate(ZonedDateTime.now(denverTimeZone).plusYears(1).toInstant());
        Vehicle v = b.getVehicle();
        if (v == null) {
            throw new AppException("Veículo não encontrado");
        }
        VehicleType vt = vtRepository.findVehicleTypeById(2);
        if (vt == null) {
            throw new AppException("Tipo de Veículo não encontrado");
        }
        v.setVehicleType(vt);
        vehicleRepository.save(v);
        b.setContractCode(vb.getContract_code());
        b.setAccepted(1);
        b.setRequest(0);
        bindRepository.save(b);
        return b;
    }

    @Override
    public Bind unvalidateBind(int bind_id) throws Exception {
        ZoneId denverTimeZone = ZoneId.of("Europe/Lisbon");
        Bind b = bindRepository.findBindById(bind_id);
        if (b == null) {
            throw new AppException("Vinculo não encontrado");
        }
        b.setStartDate(ZonedDateTime.now(denverTimeZone).toInstant());
        b.setEndDate(ZonedDateTime.now(denverTimeZone).toInstant());
        Vehicle v = b.getVehicle();
        v.setVehicleType(vtRepository.findVehicleTypeById(1));
        vehicleRepository.save(v);
        b.setRequest(0);
        b.setAccepted(0);
        bindRepository.save(b);
        return b;
    }

    @Override
    public void unbind(String user_nif) throws Exception {
        User u = userRepository.findUserByNif(user_nif);
        Bind b = bindRepository.findBindByUser(u);
        if (u == null) {
            throw new AppException("Utilizador não encontrado");
        }
        if (b == null) {
            throw new AppException("Mediador não encontrado");
        }
        b.setRequest(0);
        b.setAccepted(0);
        Vehicle v = b.getVehicle();
        if (v == null) {
            throw new AppException("Veículo não encontrado");
        }
        v.setVehicleType(vtRepository.findVehicleTypeById(1));
        vehicleRepository.save(v);
        bindRepository.save(b);
    }

    @Override
    public List<Bind> readBinds() {
        if (bindRepository.findAll().isEmpty()) {
            throw new AppException("Nenhum Vinculo encontrado");
        }
        return bindRepository.findAll();
    }

    @Override
    public Bind readBind(int bind_id) {
        Bind b = bindRepository.findBindById(bind_id);
        if (b == null) {
            throw new AppException("Vinculo não encontrado");
        }
        return b;
    }

    @Override
    public Bind updateBind(int bind_id, Bind bind) {
        if (bind == null) {
            throw new AppException("Informações do Vinculo inválidas ou não encontradas");
        }
        if (bindRepository.findBindById(bind_id) == null) {
            throw new BadRequestException("Vinculo Inválido");
        }
        bind.setId(bind_id);
        return bindRepository.save(bind);
    }

    @Override
    public List<Bind> readAllPendingBind(String broker_nif) {
        if (bindRepository.findBindByUser(userRepository.findUserByNif(broker_nif)) == null) {
           throw new AppException("Vinculos não encontrados");
        }
        List<Bind> binds = new ArrayList<>();
        for (Bind b : bindRepository.findAll()) {
            if (b.getBroker().getNif().equals(broker_nif)) {
                if (b.getRequest() == 1) {
                    binds.add(b);
                }
            }
        }
        return binds;
    }

    @Override
    public void unbindVehicle(int vehicle_id) throws Exception {
        Vehicle v = vehicleRepository.findVehicleById(vehicle_id);
        if (v == null) {
            throw new AppException("Veículo não encontrado");
        }
        Bind b = bindRepository.findBindByVehicle(v);
        if (b == null) {
            throw new AppException("Vínculo não encontrado");
        }
        b.setAccepted(0);
        b.setRequest(0);
        v.setVehicleType(vtRepository.findVehicleTypeById(1));
        vehicleRepository.save(v);
        bindRepository.save(b);
    }

}
