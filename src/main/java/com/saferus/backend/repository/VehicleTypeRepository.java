/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.repository;

import com.saferus.backend.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author lucasbrito
 */
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer>{
    
    VehicleType findVehicleTypeById(int id);
    
}
