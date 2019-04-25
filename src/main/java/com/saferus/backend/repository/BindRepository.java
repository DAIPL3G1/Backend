/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.repository;

import com.saferus.backend.model.User;
import com.saferus.backend.model.Bind;
import com.saferus.backend.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lucasbrito
 */
@Repository("bindRepository")
public interface BindRepository extends JpaRepository<Bind, Integer>{
    
    Bind findBindById(int id);
    Bind findBindByUser(User u);
    Bind findBindByVehicle(Vehicle v);
    
}
