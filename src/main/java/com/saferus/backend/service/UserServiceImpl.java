/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.model.Bind;
import com.saferus.backend.model.User;
import com.saferus.backend.model.Vehicle;
import com.saferus.backend.repository.BindRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.saferus.backend.repository.UserRepository;
import com.saferus.backend.repository.VehicleRepository;
import com.saferus.backend.repository.VehicleTypeRepository;
import java.util.ArrayList;

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

    @Override
    public List<User> readAllUsers() {
        List<User> users = new ArrayList<>();
        for (User u : userRepository.findAll()) {
            if (u.getType().equals("USER")) {
                users.add(u);
            }
        }
        return users;
    }

    @Override
    public User readUser(String user_nif) {
        return userRepository.findUserByNif(user_nif);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User updateInfo(User user, String user_nif) {
        User u = userRepository.findUserByNif(user_nif);
        user.setNif(user_nif);
        user.setAccountType(u.getAccountType());
        user.setEmail(u.getEmail());
        user.setPassword(u.getPassword());
        user.setRole(u.getRole());
        return userRepository.save(user);
    }

    @Override
    public void updatePassword(String user_nif, User user) {
        user.setNif(user_nif);
        userRepository.save(user);
    }

    @Override
    public List<User> readAllBrokers() {
        List<User> brokers = new ArrayList<>();
        for (User u : userRepository.findAll()) {
            if (u.getType().equals("BROKER")) {
                brokers.add(u);
            }
        }
        return brokers;
    }

    @Override
    public User readBroker(String broker_nif) {
        return userRepository.findUserByNif(broker_nif);
    }

    @Override
    public Vehicle addVehicleToUser(Vehicle vehicle, String user_nif) {
        vehicle.setUser(userRepository.findUserByNif(user_nif));
        vehicle.setVehicleType(vtRepository.findVehicleTypeById(1));
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(int id) {
        Vehicle vehicle = vehicleRepository.findVehicleById(id);
        vehicleRepository.delete(vehicle);
    }

    @Override
    public List<User> readAllUsersFromBroker(String broker_nif) {
        List<User> users = new ArrayList<>();
        User broker = userRepository.findUserByNif(broker_nif);
        for (Bind b : bindRepository.findAll()) {
            if (b.getBroker().equals(broker)) {
                users.add(b.getUser());
            }
        }
        return users;
    }

    @Override
    public List<Vehicle> readAllBoundVehicles(String broker_nif) {
        List<Vehicle> vehicles = new ArrayList<>();
        User broker = userRepository.findUserByNif(broker_nif);
        for (Bind b : bindRepository.findAll()) {
            if (b.getBroker().equals(broker)) {
                Vehicle v = b.getVehicle();
                vehicles.add(v);
            }
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> readAllVehiclesFromUser(String user_nif) {
        List<Vehicle> vehicles = new ArrayList<>();
        User user = userRepository.findUserByNif(user_nif);
        for (Vehicle v : vehicleRepository.findAll()) {
            if (v.getUser().equals(user)) {
                vehicles.add(v);
            }
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> readAllVehicles() {
        return vehicleRepository.findAll();
    }

}
