/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.model;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 *
 * @author lucasbrito
 */
public class TripData {

    @Id
    public ObjectId _id;
    
    public int vehicle_id;
    public List<Data> datas;

    public TripData() {}
    
    public TripData(ObjectId _id, int vehicle_id, List<Data> datas) {
        this._id = _id;
        this.vehicle_id = vehicle_id;
        this.datas = datas;
    }

    public String get_Id() {
        return _id.toHexString();
    }

    public void set_Id(ObjectId id) {
        this._id = id;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

}
