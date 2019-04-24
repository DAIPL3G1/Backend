/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.repository;

import com.saferus.backend.model.TripTratment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lucasbrito
 */
@Repository("tripTratmentRepository")
public interface TripTratmentRepository extends MongoRepository<TripTratment, Integer>{
    
    TripTratment findBy_id(ObjectId _id);
    
}
