/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.exceptions.DataNotFoundException;
import com.saferus.backend.model.TripTratment;
import com.saferus.backend.repository.TripTratmentRepository;
import com.saferus.backend.repository.VehicleRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lucasbrito
 */
@Service("tripService")
public class TripServiceImpl implements TripService {

    @Autowired
    private TripTratmentRepository tripTratmentRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<TripTratment> readTripsFromVehicle(int vehicle_id) {
        if (vehicleRepository.findVehicleById(vehicle_id) == null) {
            throw new DataNotFoundException("Veículo não encontrado");
        }
        List<TripTratment> trips = new ArrayList<>();
        for (TripTratment trip : tripTratmentRepository.findAll()) {
            if (trip.getVehicle_id() == vehicle_id) {
                trips.add(trip);
            }
        }
        return trips;
    }

}
