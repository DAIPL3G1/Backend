package com.saferus.backend.modelo;

import java.io.Serializable;
import static java.lang.Math.random;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Vinculacao implements Serializable {

    @Id
    private long id;
    
    @NotNull
    private UUID codigo;
    
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
    
    @NotNull
    private int valida;

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

    public int getValida() {
        return valida;
    }

    public void setValida(int valida) {
        this.valida = valida;
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(UUID codigo) {
        this.codigo = codigo;
    }
    
    
    
    
}
