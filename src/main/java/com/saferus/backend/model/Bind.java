package com.saferus.backend.model;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Bind")
public class Bind implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "contract_code")
    private String contractCcode;

    @Column(name = "startDate")
    private Instant startDate;

    @Column(name = "endDate")
    private Instant endDate;

    @NotNull
    @Column(name = "requestDate")
    private Instant requestDate;

    @OneToOne
    @NotNull
    private User user;

    @ManyToOne
    @NotNull
    private User broker;

    @OneToOne
    @NotNull
    private Vehicle vehicle;
    
    private int accepted;
    
    private int request;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Instant requestDate) {
        this.requestDate = requestDate;
    }

    public String getContractCode() {
        return contractCcode;
    }

    public void setContractCode(String code) {
        this.contractCcode = code;
    }

    public User getUser() {
          return user;
    }

    public void setUser(User user) {
        if (user.getAccountType().getId() == 1) {
            this.user = user;
        }
    }

    public User getBroker() {
        return broker;
    }

    public void setBroker(User broker) {
        if (broker.getAccountType().getId() == 2) {
            this.broker = broker;
        }
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }
    
    
    

}
