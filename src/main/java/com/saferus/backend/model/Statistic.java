/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.model;

import org.springframework.data.annotation.Id;
import org.bson.types.ObjectId;

/**
 *
 * @author lucasbrito
 */
public class Statistic {
    
    @Id
    private ObjectId id;
    
    private String type;
    
    private int value;
    
    public Statistic(ObjectId id, String type, int value){
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public String getId() {
        return id.toHexString();
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
}
