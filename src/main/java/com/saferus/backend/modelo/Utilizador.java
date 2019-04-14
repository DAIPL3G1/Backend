package com.saferus.backend.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Utilizador implements Serializable {

    @Id
    private long id;

    @NotNull
    private String nome;

    @NotNull
    private String apelido;

    @NotNull
    @OneToOne
    Conta conta;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
    
    public String getPassword(){
        return conta.getPassword();
    }
    
    public void setPassword(String novapassword){
        conta.setPassword(novapassword);
    }
}
