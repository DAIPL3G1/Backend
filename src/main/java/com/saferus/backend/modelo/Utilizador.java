package com.saferus.backend.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Utilizador")
public class Utilizador extends Conta implements Serializable {

    @NotNull
    @Column(name = "nome")
    private String nome;

    @NotNull
    @Column(name = "apelido")
    private String apelido;
    
    @NotNull
    @Column(name = "nif")
    private int nif;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public void setPassword(String password){
        this.password = password;
    }
    
    public String getPassword(){
        return password;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }
    
    
}
