package com.saferus.backend.model;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User extends Account implements Serializable {

    @NotNull
    @Column(name = "firstname")
    private String firstname;

    @NotNull
    @Column(name = "lastname")
    private String lastname;
    
    @Column(name = "birth_date")
    private Instant birthDate;
    
    @Column(name = "contact")
    private String contact;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "zip_code")
    private String zipCode;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "country")
    private String country;
    
    public String getType(){
        return accountType.getName();
    }
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public void setPassword(String password){
        this.password = password;
    }
    
    @Override
    public String getPassword(){
        return password;
    }
    
    @Override
    public String getEmail(){
        return email;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    @Override
    public void setEnabled(int enabled){
        this.enabled = enabled;
    }
    
    
    
}
