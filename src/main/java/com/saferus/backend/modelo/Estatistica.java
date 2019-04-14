/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.modelo;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;

/**
 *
 * @author lucasbrito
 */

@Entity
public class Estatistica implements Serializable {
    
    @Id
    private ObjectId id;
    
    @NotNull
    private double velocidade;
    
    @NotNull
    private Instant dataInicio;
    
    private Instant dataFim;
    
    @NotNull
    private Instant dataCalculo;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    public Instant getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Instant getDataFim() {
        return dataFim;
    }

    public void setDataFim(Instant dataFim) {
        this.dataFim = dataFim;
    }

    public Instant getDataCalculo() {
        return dataCalculo;
    }

    public void setDataCalculo(Instant dataCalculo) {
        this.dataCalculo = dataCalculo;
    }
    
    
    
}
