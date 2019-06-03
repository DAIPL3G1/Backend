/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controller;

import com.saferus.backend.MyTimeTask;
import com.saferus.backend.model.TripData;
import com.saferus.backend.model.TripTratment;
import com.saferus.backend.service.TripServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import static com.saferus.backend.BackendApplication.task;

/**
 *
 * @author lucasbrito
 */
@RestController
@RequestMapping("/trip")
public class TripController {
    
    @Autowired
    private TripServiceImpl tripService;
    
    @RequestMapping(value = "/read/{vehicle_id}", method = RequestMethod.GET)
    public List<TripTratment> readTripsFromVehicle(@PathVariable("vehicle_id") int vehicle_id){
        return tripService.readTripsFromVehicle(vehicle_id);
    }
    
    @RequestMapping(value = "/read/datas/{vehicle_id}", method = RequestMethod.GET)
    public List<TripData> readTripsDatasFromVehicle(@PathVariable("vehicle_id") int vehicle_id){
        return tripService.readTripsDatasFromVehicle(vehicle_id);
    }
    
    @RequestMapping(value = "/tratment", method = RequestMethod.GET)
    public void tripTratment(){
        task.run();
    }
    
}
