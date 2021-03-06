/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.exceptions.DataNotFoundException;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            if (u.getAccountType().getId() == 1) {
                users.add(u);
            }
        }
        if (users.isEmpty()) {
            throw new DataNotFoundException("Nenhum Utilizador encontrado");
        }
        return users;
    }

    @Override
    public User readUser(String user_nif) {
        if (userRepository.findUserByNif(user_nif) == null) {
            throw new DataNotFoundException("Utilizador não encontrado");
        }
        return userRepository.findUserByNif(user_nif);
    }

    @Override
    public User findUserByEmail(String email) {
        User u = userRepository.findUserByEmail(email);
        if (u == null) {
            throw new DataNotFoundException("Utilizador não encontrado");
        }
        return u;
    }

    @Override
    public User updateInfo(User user, String user_nif) {
        User u = userRepository.findUserByNif(user_nif);
        if (u == null) {
            throw new DataNotFoundException("Utilizador não encontrado");
        }
        user.setNif(user_nif);
        user.setAccountType(u.getAccountType());
        user.setEmail(u.getEmail());
        user.setPassword(u.getPassword());
        user.setEnabled(1);
        return userRepository.save(user);
    }

    @Override
    public void updatePassword(String user_nif, User user) {
        User u = userRepository.findUserByNif(user_nif);
        if (u == null) {
            throw new DataNotFoundException("Utilizador não encontrado");
        }
        u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        u.setNif(user_nif);
        userRepository.save(u);
    }

    @Override
    public List<User> readAllBrokers() {
        List<User> brokers = new ArrayList<>();
        for (User b : userRepository.findAll()) {
            if (b.getAccountType().getId() == 2) {
                brokers.add(b);
            }
        }
        if (brokers.isEmpty()) {
            throw new DataNotFoundException("Nenhum Mediador encontrado");
        }
        return brokers;
    }

    @Override
    public User readBroker(String broker_nif) {
        User b = userRepository.findUserByNif(broker_nif);
        if (b == null) {
            throw new DataNotFoundException("Mediador não encontrado");
        }
        return userRepository.findUserByNif(broker_nif);
    }

    @Override
    public Vehicle addVehicleToUser(Vehicle vehicle, String user_nif) {
        if (vehicleRepository.findVehicleById(vehicle.getId()) != null) {
            throw new DataNotFoundException("Veículo já inserido");
        }
        if (userRepository.findUserByNif(user_nif) == null) {
            throw new DataNotFoundException("Utilizador não encontrado");
        }
        vehicle.setUser(userRepository.findUserByNif(user_nif));
        vehicle.setVehicleType(vtRepository.findVehicleTypeById(1));
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(int vehicle_id) {
        Vehicle vehicle = vehicleRepository.findVehicleById(vehicle_id);
        if (vehicle == null) {
            throw new DataNotFoundException("Veículo não encontrado");
        }
        vehicleRepository.delete(vehicle);
    }

    @Override
    public List<User> readAllClientsFromBroker(String broker_nif) {

        User broker = userRepository.findUserByNif(broker_nif);
        if (broker == null) {
            throw new DataNotFoundException("Mediador não encontrado");
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
            throw new DataNotFoundException("Mediador não tem clientes");
        }
        return users;
    }

    @Override
    public List<Vehicle> readAllBoundVehicles(String broker_nif) {
        List<Vehicle> vehicles = new ArrayList<>();
        User broker = userRepository.findUserByNif(broker_nif);
        if (broker == null) {
            throw new DataNotFoundException("Mediador não encontrado");
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
            throw new DataNotFoundException("Nenhum Veículo Vinculado encontrado");
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> readAllVehiclesFromUser(String user_nif) {
        List<Vehicle> vehicles = new ArrayList<>();
        User user = userRepository.findUserByNif(user_nif);
        if (user == null) {
            throw new DataNotFoundException("Utiliador não encontrado");
        }
        for (Vehicle v : vehicleRepository.findAll()) {
            if (v.getUser().equals(user)) {
                vehicles.add(v);
            }
        }
        if (vehicles.isEmpty()) {
            throw new DataNotFoundException("Utilizador não tem qualquer veículo");
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> readAllVehicles() {
        if (vehicleRepository.findAll().isEmpty()) {
            throw new DataNotFoundException("Nenhum Veículo encontrado");
        }
        return vehicleRepository.findAll();
    }

    @Override
    public void setAuthCookieToResonse(final HttpServletRequest request, final HttpServletResponse response) throws UnsupportedEncodingException{

        String cookieKey = "auth";
        String cookieValue = request.getHeader("Authorization");

        if (cookieValue != null) {
            Cookie cookie = new Cookie(cookieKey, cookieValue);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            response.addCookie(cookie);
            System.out.println("cookie stored " + cookieKey);
        }

    }

}
