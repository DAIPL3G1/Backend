/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.repository;

import com.saferus.backend.model.User;
import com.saferus.backend.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lucasbrito
 */
@Repository("vehicleRepository")
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    
    Vehicle findVehicleById(int id);
    Vehicle findVehicleByPlate(String plate);
    Vehicle findVehicleByUser(User u);
    
}
