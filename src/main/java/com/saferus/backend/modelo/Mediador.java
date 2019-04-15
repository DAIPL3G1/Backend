package com.saferus.backend.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Mediador")
public class Mediador extends Utilizador implements Serializable {
    
    @Override
    public String getPassword(){
        return password;
    }
    
    @Override
    public void setPassword(String password){
        this.password = password;
    }
    
    @Override
    public int getAtivo(){
        return ativo;
    }
    
    @Override
    public void setAtivo(int i) {
        this.ativo = i;
    }
}
