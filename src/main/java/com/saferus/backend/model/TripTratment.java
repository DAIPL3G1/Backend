/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.bson.types.ObjectId;

/**
 *
 * @author lucasbrito
 */
public class TripTratment {
    
    @Id
    public ObjectId _id;
    
    public int vehicle_id;
    public Date startDate;
    public Date finishDate;
    public double latitudeStart;
    public double longitudeStart;
    public double latitudeFinish;
    public double longitudeFinish;
    public long tripTime;
    public double distance;
    public long aboveVelocityLimitTime;
    public double velocityAverage;

    public TripTratment(){}

    public TripTratment(ObjectId _id, int vehicle_id, Date startDate, Date finishDate, double latitudeStart, double longitudeStart, double latitudeFinish, double longitudeFinish, long tripTime, double distance, long aboveVelocityLimitTime, double velocityAverage) {
        this._id = _id;
        this.vehicle_id = vehicle_id;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.latitudeStart = latitudeStart;
        this.longitudeStart = longitudeStart;
        this.latitudeFinish = latitudeFinish;
        this.longitudeFinish = longitudeFinish;
        this.tripTime = tripTime;
        this.distance = distance;
        this.aboveVelocityLimitTime = aboveVelocityLimitTime;
        this.velocityAverage = velocityAverage;
    }
    

    public String getId() {
        return _id.toHexString();
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public long getTripTime() {
        return tripTime;
    }

    public void setTripTime(long tripTime) {
        this.tripTime = tripTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getAboveVelocityLimitTime() {
        return aboveVelocityLimitTime;
    }

    public void setAboveVelocityLimitTime(long aboveVelocityLimitTime) {
        this.aboveVelocityLimitTime = aboveVelocityLimitTime;
    }

    public double getVelocityAverage() {
        return velocityAverage;
    }

    public void setVelocityAverage(double velocityAverage) {
        this.velocityAverage = velocityAverage;
    }

    public double getLatitudeStart() {
        return latitudeStart;
    }

    public void setLatitudeStart(double latitudeStart) {
        this.latitudeStart = latitudeStart;
    }

    public double getLongitudeStart() {
        return longitudeStart;
    }

    public void setLongitudeStart(double longitudeStart) {
        this.longitudeStart = longitudeStart;
    }

    public double getLatitudeFinish() {
        return latitudeFinish;
    }

    public void setLatitudeFinish(double latitudeFinish) {
        this.latitudeFinish = latitudeFinish;
    }

    public double getLongitudeFinish() {
        return longitudeFinish;
    }

    public void setLongitudeFinish(double longitudeFinish) {
        this.longitudeFinish = longitudeFinish;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
    
    
    
    
    
    
    
}
