/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.exceptions.BadRequestException;
import com.saferus.backend.model.User;
import com.saferus.backend.model.Vehicle;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lucasbrito
 */
public interface UserService {

    public List<User> readAllUsers();

    public User readUser(String user_nif);

    public User updateInfo(User user, String user_nif);

    public void updatePassword(String user_nif, User user) ;

    public List<User> readAllBrokers();

    public User readBroker(String broker_nif);

    public User findUserByEmail(String email);
    
    public List<User> readAllClientsFromBroker(String broker_nif);
    
    public void deleteVehicle(int vehicle_id);
    
    public Vehicle addVehicleToUser(Vehicle vehicle, String user_nif);
    
    public List<Vehicle> readAllBoundVehicles(String broker_nif);
    
    public List<Vehicle> readAllVehiclesFromUser(String user_nif);
    
    public List<Vehicle> readAllVehicles();
    
    public void setAuthCookieToResonse(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException;

    public String authenticateUser(String email, String password, HttpServletResponse response) throws BadRequestException;
    
    public String forgetPassword(String email);
    
    public String changePw(String Email);
}
