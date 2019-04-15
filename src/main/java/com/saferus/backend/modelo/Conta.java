package com.saferus.backend.modelo;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(name = "email")
     String email;

    @NotNull
    @Column(name = "password")
    String password;

    @NotNull
    @Column(name = "ativo")
    int ativo;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Conta_TipoConta", joinColumns = @JoinColumn(name = "conta_id"), inverseJoinColumns = @JoinColumn(name = "tipoconta_id"))
    Set<TipoConta> tipoConta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public Set<TipoConta> getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(Set<TipoConta> tipoConta) {
        this.tipoConta = tipoConta;
    }

}
