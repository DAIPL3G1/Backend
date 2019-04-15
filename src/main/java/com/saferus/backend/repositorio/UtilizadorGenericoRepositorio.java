/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.repositorio;

import com.saferus.backend.modelo.UtilizadorGenerico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lucasbrito
 */
@Repository("ugRepositorio")
public interface UtilizadorGenericoRepositorio extends JpaRepository<UtilizadorGenerico, Integer>{
    
    UtilizadorGenerico findUtilizadorGenericoById(int id);
    
}