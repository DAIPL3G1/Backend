/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.repositorio;

import com.saferus.backend.modelo.Segurado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lucasbrito
 */
@Repository("seguradoRepositorio")
public interface SeguradoRepositorio extends JpaRepository<Segurado, Long>{
    public List<Segurado> lerTodos();
    public Segurado ler(long id);
    public Segurado alterar(Segurado alteracao, long id);
    public Segurado criar(Segurado novo);
    public boolean remover(long id);
    
}
