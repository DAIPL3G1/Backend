package com.saferus.backend.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class UtilizadorGenerico extends Utilizador implements Serializable {

    @Id
    private long id;

    @NotNull
    private String nif;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    @Override
    public String getPassword() {
        return conta.getPassword();
    }

    public void setPassword(String novapassword) {
        conta.setPassword(novapassword);
    }
    
    public int getAtivo(){
        return conta.getAtivo();
    }

    public void setAtivo(int i) {
        conta.setAtivo(i);
    }
}
