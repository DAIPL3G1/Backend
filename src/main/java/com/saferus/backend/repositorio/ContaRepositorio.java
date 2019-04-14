/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.repositorio;

import com.saferus.backend.modelo.Conta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lucasbrito
 */
public interface ContaRepositorio extends JpaRepository<Conta, Long>{
    public List<Conta> lerTodos();
    public Conta ler(long id);
    public Conta alterar(Conta alteracao, long id);
    public boolean remover(long id);
    
}
