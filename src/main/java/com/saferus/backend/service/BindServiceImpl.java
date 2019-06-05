/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.exceptions.BadRequestException;
import com.saferus.backend.exceptions.DataNotFoundException;
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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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

    //Função para o Pedido de Vinculação (Recebe Matricula, NIF Mediador e NIF Utilizador)
    @Override
    public void requestBind(String plate, String broker_nif, String user_nif) {
        Bind newBind = new Bind();
        User broker = userRepository.findUserByNif(broker_nif);
        User user = userRepository.findUserByNif(user_nif);
        Vehicle vehicle = vehicleRepository.findVehicleByPlate(plate);
        if (broker == null) {
            throw new DataNotFoundException("Mediador não encontrado");
        } else if (user == null) {
            throw new DataNotFoundException("Utilizador não encontrado");
        } else if (vehicle == null) {
            throw new DataNotFoundException("Veículo não encontrado");
        }
        newBind.setBroker(broker);
        newBind.setUser(user);
        newBind.setVehicle(vehicle);
        newBind.setRequestDate(Instant.now());
        newBind.setRequest(1);
        newBind.setAccepted(0);
        bindRepository.save(newBind);
    }

    //Função para Validar a Vinculação (Recebe Objeto ValidateBind e ID da Vinculação)
    @Override
    public Bind validateBind(ValidateBind vb, int bind_id) throws Exception {
        ZoneId denverTimeZone = ZoneId.of("Europe/Lisbon");
        Bind b = bindRepository.findBindById(bind_id);
        if (b == null) {
            throw new DataNotFoundException("Vinculo não encontrado");
        }
        b.setStartDate(ZonedDateTime.now(denverTimeZone).toInstant());
        b.setEndDate(ZonedDateTime.now(denverTimeZone).plusYears(1).toInstant());
        Vehicle v = b.getVehicle();
        if (v == null) {
            throw new DataNotFoundException("Veículo não encontrado");
        }
        VehicleType vt = vtRepository.findVehicleTypeById(2);
        if (vt == null) {
            throw new DataNotFoundException("Tipo de Veículo não encontrado");
        }
        v.setVehicleType(vt);
        vehicleRepository.save(v);
        b.setContractCode(vb.getContractCode());
        b.setAccepted(1);
        b.setRequest(0);
        bindRepository.save(b);
        return b;
    }

    //Função para Invalidar Vinculação (Recebe o ID da Vinculação)
    @Override
    public Bind unvalidateBind(int bind_id) throws Exception {
        ZoneId denverTimeZone = ZoneId.of("Europe/Lisbon");
        Bind b = bindRepository.findBindById(bind_id);
        if (b == null) {
            throw new DataNotFoundException("Vinculo não encontrado");
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

    //Função para desvincular (Recebe o NIF do Utilizador)
    @Override
    public void unbind(String user_nif) throws Exception {
        User u = userRepository.findUserByNif(user_nif);
        Bind b = bindRepository.findBindByUser(u);
        if (u == null) {
            throw new DataNotFoundException("Utilizador não encontrado");
        }
        if (b == null) {
            throw new DataNotFoundException("Mediador não encontrado");
        }
        b.setRequest(0);
        b.setAccepted(0);
        Vehicle v = b.getVehicle();
        if (v == null) {
            throw new DataNotFoundException("Veículo não encontrado");
        }
        v.setVehicleType(vtRepository.findVehicleTypeById(1));
        vehicleRepository.save(v);
        bindRepository.save(b);
    }

    //Função para ler todas as Vinculações
    @Override
    public List<Bind> readBinds() {
        return bindRepository.findAll();
    }

    //Função para ler uma Vinculação (Recebe o ID da Vinculação)
    @Override
    public Bind readBind(int bind_id) {
        Bind b = bindRepository.findBindById(bind_id);
        if (b == null) {
            throw new DataNotFoundException("Vinculo não encontrado");
        }
        return b;
    }

    //Função para alterar uma Vinculação (Recebe o ID da Vinculação e o Objeto BIND)
    @Override
    public Bind updateBind(int bind_id, Bind bind) {
        if (bind == null) {
            throw new DataNotFoundException("Informações do Vinculo inválidas ou não encontradas");
        }
        if (bindRepository.findBindById(bind_id) == null) {
            throw new BadRequestException("Vinculo Inválido");
        }
        bind.setId(bind_id);
        return bindRepository.save(bind);
    }

    //Função para ler todas as Vinculações em Aprovação (Recebe o NIF do Mediador)
    @Override
    public List<Bind> readAllPendingBind(String broker_nif) {
        User broker = userRepository.findUserByNif(broker_nif);
        List<Bind> binds = new ArrayList<>();
        for (Bind b : bindRepository.findAll()) {
            if (b.getBroker().getNif().equals(broker_nif)) {
                if (b.getRequest() == 1) {
                    binds.add(b);
                }
            } else {
                throw new DataNotFoundException("Nenhum pedido de vinculação encontrado!");
            }
        }
        return binds;
    }

    //Função para desvincular Veículo (Recebe o ID do Veículo)
    @Override
    public void unbindVehicle(int vehicle_id) throws Exception {
        Vehicle v = vehicleRepository.findVehicleById(vehicle_id);
        if (v == null) {
            throw new DataNotFoundException("Veículo não encontrado");
        }
        Bind b = bindRepository.findBindByVehicle(v);
        if (b == null) {
            throw new DataNotFoundException("Vínculo não encontrado");
        }
        b.setAccepted(0);
        b.setRequest(0);
        bindRepository.save(b);
        v.setVehicleType(vtRepository.findVehicleTypeById(1));
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("Unbind-Logs.txt"));
        for (Bind bind : bindRepository.findAll()) {
            if (bind.getAccepted() == 0 && bind.getRequest() == 0) {
                writer.writeObject("Bind id=" + bind.getId() + 
                               " Contract Code=" + bind.getContractCode() +
                               " Request Date=" + bind.getRequestDate() + 
                               " Start Date=" + bind.getStartDate() + 
                               " End Date=" + bind.getEndDate() + 
                               " Mediador=" + bind.getBroker().getNif() + 
                               " Utilizador=" + bind.getUser().getNif() + 
                               " Veículo=" + bind.getVehicle().getPlate() + 
                               " da marca " + bind.getVehicle().getBrand() + 
                               " foi desvinculado!");
            }
        }
        writer.close();
        vehicleRepository.save(v);
        bindRepository.delete(b);
    }

}
