package com.saferus.backend.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Mediador extends Utilizador implements Serializable {

    @Id
    private long id;

    @NotNull
    private String nif;

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
    
    public int getAtivo(){
        return conta.getAtivo();
    }
    
    public void setAtivo(int i) {
        conta.setAtivo(i);
    }
}
