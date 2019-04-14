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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author lucasbrito
 */

@Entity
public class Vinculacao implements Serializable {
    
    @Id
    private long id;
    
    @NotNull
    private Instant dataInicio;
    
    @NotNull
    private Instant dataFim;
    
    @NotNull
    private Instant dataPedido;
    
    @OneToOne
    @NotNull
    private Segurado segurado;
    
    @ManyToOne
    @NotNull
    private Mediador mediador;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Instant getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Instant dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Segurado getSegurado() {
        return segurado;
    }

    public void setSegurado(Segurado segurado) {
        this.segurado = segurado;
    }

    public Mediador getMediador() {
        return mediador;
    }

    public void setMediador(Mediador mediador) {
        this.mediador = mediador;
    }
    
    
    
}
