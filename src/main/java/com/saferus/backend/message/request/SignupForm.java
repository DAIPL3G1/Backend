/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.message.request;

/**
 *
 * @author lucasbrito
 */
import java.util.Set;
 
import javax.validation.constraints.*;
 
public class SignupForm {
    
    @NotBlank
    @Size(min = 9, max = 9)
    private String nif;
    
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstname;
 
    @NotBlank
    @Size(min = 3, max = 50)
    private String lastname;
 
    @NotBlank
    @Size(max = 60)
    @Email
    private String email;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
    private Set<String> role;
 
    public String getFirstame() {
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
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
    
}