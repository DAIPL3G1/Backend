/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.repository;

import com.saferus.backend.model.Datas;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author lucasbrito
 */
public interface StatisticRepository extends MongoRepository<Datas, String>{
    
    public Datas findStatisticById(ObjectId id);
    
}
