/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.exceptions.AppException;
import com.saferus.backend.model.Bind;
import com.saferus.backend.model.Role;
import com.saferus.backend.model.User;
import com.saferus.backend.model.Vehicle;
import com.saferus.backend.repository.BindRepository;
import com.saferus.backend.repository.RoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.saferus.backend.repository.UserRepository;
import com.saferus.backend.repository.VehicleRepository;
import com.saferus.backend.repository.VehicleTypeRepository;
import java.util.ArrayList;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author lucasbrito
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BindRepository bindRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleTypeRepository vtRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<User> readAllUsers() {
        List<User> users = new ArrayList<>();
        for (User u : userRepository.findAll()) {

            for (Role role : roleRepository.findAll()) {
                if (role.getId() == 1) {
                    users.add(u);
                }
            }
        }
        if (users.isEmpty()) {
           throw new AppException("Nenhum Utilizador encontrado");
        }
        return users;
    }

    @Override
    public User readUser(String user_nif) {
        if (userRepository.findUserByNif(user_nif) == null) {
            throw new AppException("Utilizador não encontrado");
        }
        return userRepository.findUserByNif(user_nif);
    }

    @Override
    public User findUserByEmail(String email) {
        User u = userRepository.findUserByEmail(email);
        if (u == null) {
            throw new AppException("Utilizador não encontrado");
        }
        return u;
    }

    @Override
    public User updateInfo(User user, String user_nif) {
        User u = userRepository.findUserByNif(user_nif);
        if (u == null) {
            throw new AppException("Utilizador não encontrado");
        }
        user.setNif(user_nif);
        user.setRoles(user.getRoles());
        user.setEmail(u.getEmail());
        user.setPassword(u.getPassword());
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    public void updatePassword(String user_nif, User user) {
        User u = userRepository.findUserByNif(user_nif);
        if (u == null) {
            throw new AppException("Utilizador não encontrado");
        }
        u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        u.setNif(user_nif);
        userRepository.save(u);
    }

    @Override
    public List<User> readAllBrokers() {
        List<User> brokers = new ArrayList<>();
        for (User b : userRepository.findAll()) {
            for(Role role: roleRepository.findAll()){
                if(role.getId() == 2){
                    brokers.add(b);
                }
            }
        }
        if (brokers.isEmpty()) {
            throw new AppException("Nenhum Mediador encontrado");
        }
        return brokers;
    }

    @Override
    public User readBroker(String broker_nif) {
        User b = userRepository.findUserByNif(broker_nif);
        if (b == null) {
            throw new AppException("Mediador não encontrado");
        }
        return userRepository.findUserByNif(broker_nif);
    }

    @Override
    public Vehicle addVehicleToUser(Vehicle vehicle, String user_nif) {
        if (vehicleRepository.findVehicleById(vehicle.getId()) == null) {
            throw new AppException("Veículo não encontrado");
        }
        if (userRepository.findUserByNif(user_nif) == null) {
            throw new AppException("Utilizador não encontrado");
        }
        vehicle.setUser(userRepository.findUserByNif(user_nif));
        vehicle.setVehicleType(vtRepository.findVehicleTypeById(1));
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(int vehicle_id) {
        Vehicle vehicle = vehicleRepository.findVehicleById(vehicle_id);
        if (vehicle == null) {
            throw new AppException("Veículo não encontrado");
        }
        vehicleRepository.delete(vehicle);
    }

    @Override
    public List<User> readAllClientsFromBroker(String broker_nif) {

        User broker = userRepository.findUserByNif(broker_nif);
        if (broker == null) {
            throw new AppException("Mediador não encontrado");
        }

        List<User> users = new ArrayList<>();

        for (Bind b : bindRepository.findAll()) {
            if (b.getAccepted() == 1) {
                if (b.getBroker().equals(broker)) {
                    if (!users.contains(b.getUser())) {
                        users.add(b.getUser());
                    }
                }
            }
        }
        if (users.isEmpty()) {
            throw new AppException("Mediador não tem clientes");
        }
        return users;
    }

    @Override
    public List<Vehicle> readAllBoundVehicles(String broker_nif) {
        List<Vehicle> vehicles = new ArrayList<>();
        User broker = userRepository.findUserByNif(broker_nif);
        if (broker == null) {
            throw new AppException("Mediador não encontrado");
        }
        for (Bind b : bindRepository.findAll()) {
            if (b.getAccepted() == 1) {
                if (b.getBroker().equals(broker)) {
                    Vehicle v = b.getVehicle();
                    if (v.getVehicleType().getId() == 2) {
                        vehicles.add(v);
                    }
                }
            }
        }
        if (vehicles.isEmpty()) {
            throw new AppException("Nenhum Veículo Vinculado encontrado");
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> readAllVehiclesFromUser(String user_nif) {
        List<Vehicle> vehicles = new ArrayList<>();
        User user = userRepository.findUserByNif(user_nif);
        if (user == null) {
            throw new AppException("Utiliador não encontrado");
        }
        for (Vehicle v : vehicleRepository.findAll()) {
            if (v.getUser().equals(user)) {
                vehicles.add(v);
            }
        }
        if (vehicles.isEmpty()) {
            throw new AppException("Utilizador não tem qualquer veículo");
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> readAllVehicles() {
        if (vehicleRepository.findAll().isEmpty()) {
            throw new AppException("Nenhum Veículo encontrado");
        }
        return vehicleRepository.findAll();
    }

}
