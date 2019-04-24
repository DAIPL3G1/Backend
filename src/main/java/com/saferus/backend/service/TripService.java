/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.model.TripTratment;
import java.util.List;

/**
 *
 * @author lucasbrito
 */
public interface TripService {
    
public List<TripTratment> readTripsFromVehicle(int vehicle_id);
    
}
