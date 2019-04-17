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
    private String contract_code;

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

    @NotNull
    @Column(name = "enabled")
    private int enabled;

    @NotNull
    @OneToOne
    private Vehicle vehicle;

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

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getContractCode() {
        return contract_code;
    }

    public void setContractCode(String code) {
        this.contract_code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user.getType().equals("USER")) {
            this.user = user;
        }
    }

    public User getBroker() {
        return broker;
    }

    public void setBroker(User broker) {
        if (broker.getType().equals("BROKER")) {
            this.broker = broker;
        }
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}