/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.repositorio;

import com.saferus.backend.modelo.Vinculacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lucasbrito
 */
@Repository("vinculacaoRepositorio")
public interface VinculacaoRepositorio extends JpaRepository<Vinculacao, Long>{
    
    public List<Vinculacao> lerTodos();
    public Vinculacao ler(long id);
    public Vinculacao alterar(Vinculacao alteracao, long id);
    public Vinculacao criar(Vinculacao nova);
    public boolean remover(long id);
    
}
