/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.model;

import java.util.Date;
import org.springframework.data.annotation.Id;

/**
 *
 * @author lucasbrito
 */
public class Data {
    
    @Id
    private int idVeiculo;
    
    private Date date;
    
    private int velocity;
    
    private boolean start;
    
    private boolean finish;
    
    private double latitude;
    
    private double longitude;
    
    private int velocityLimit;

    public Data(int idVeiculo, Date date, int velocity, boolean start, boolean finish, double latitude, double longitude, int velocityLimit) {
        this.idVeiculo = idVeiculo;
        this.date = date;
        this.velocity = velocity;
        this.start = start;
        this.finish = finish;
        this.latitude = latitude;
        this.longitude = longitude;
        this.velocityLimit = velocityLimit;
    }
    

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int id) {
        this.idVeiculo = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getVelocityLimit() {
        return velocityLimit;
    }

    public void setVelocityLimit(int velocityLimit) {
        this.velocityLimit = velocityLimit;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
    
    
    
    
}
