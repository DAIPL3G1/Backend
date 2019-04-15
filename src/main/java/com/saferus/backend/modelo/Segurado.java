package com.saferus.backend.modelo;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Segurado")
public class Segurado extends UtilizadorGenerico implements Serializable {
    
    @OneToOne
    private Vinculacao vinculacao;
    
    /*@OneToMany(mappedBy = "utilizador", cascade = CascadeType.ALL)
    private final Set<Veiculo> veiculos;*/
    
    /*public Segurado(Veiculo veiculos){
        this.veiculos = Stream.of(veiculos).collect(Collectors.toSet());
        this.veiculos.forEach(x -> x.setUtilizador(this));
    }*/

    public Vinculacao getVinculacao() {
        return vinculacao;
    }

    public void setVinculacao(Vinculacao vinculacao) {
        this.vinculacao = vinculacao;
    }
    
    
    
    
}
