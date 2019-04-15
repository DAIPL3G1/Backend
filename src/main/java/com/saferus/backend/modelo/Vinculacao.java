package com.saferus.backend.modelo;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
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
@Table(name = "Vinculacao")
public class Vinculacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @NotNull
    @Column(name = "codigo")
    private UUID codigo;
    
    @Column(name = "dataInicio")
    private Instant dataInicio;

    @Column(name = "dataFim")
    private Instant dataFim;

    @NotNull
    @Column(name = "dataPedido")
    private Instant dataPedido;

    @OneToOne
    @NotNull
    private Utilizador segurado;

    @ManyToOne
    @NotNull
    private Utilizador mediador;
    
    @NotNull
    @Column(name = "valida")
    private int valida;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Utilizador getSegurado() {
        return segurado;
    }

    public void setSegurado(Utilizador segurado) {
        this.segurado = segurado;
    }

    public Utilizador getMediador() {
        return mediador;
    }

    public void setMediador(Utilizador mediador) {
        this.mediador = mediador;
    }
    
    
    
    
    
    
}
