package com.saferus.backend.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Segurado extends UtilizadorGenerico implements Serializable {

    @Id
    private long id;
    
    @NotNull
    @OneToOne
    private UtilizadorGenerico utilizadorGenerico;
    
    @NotNull
    @OneToOne
    private Vinculacao vinculacao;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public UtilizadorGenerico getUtilizadorGenerico() {
        return utilizadorGenerico;
    }

    public void setUtilizadorGenerico(UtilizadorGenerico utilizadorGenerico) {
        this.utilizadorGenerico = utilizadorGenerico;
    }

    public Vinculacao getVinculacao() {
        return vinculacao;
    }

    public void setVinculacao(Vinculacao vinculacao) {
        this.vinculacao = vinculacao;
    }
    
    
    
}
