/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.repositorio;

import com.saferus.backend.modelo.Utilizador;
import com.saferus.backend.modelo.Vinculacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lucasbrito
 */
@Repository("vinculacaoRepositorio")
public interface VinculacaoRepositorio extends JpaRepository<Vinculacao, Integer>{
    
    Vinculacao findVinculacaoById(int id);
    Vinculacao findVinculacaoBySegurado(Utilizador s);
    
}
