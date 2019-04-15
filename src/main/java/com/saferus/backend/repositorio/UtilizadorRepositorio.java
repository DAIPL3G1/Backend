/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.repositorio;

import com.saferus.backend.modelo.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author lucasbrito
 */
public interface UtilizadorRepositorio extends JpaRepository<Utilizador, Integer>{
    
    Utilizador findUtilizadorGenericoById(int id);
    Utilizador findMediadorById(int id);
    Utilizador findSeguradoById(int id);
    Utilizador findUtilizadorByEmail(String email);
    
}
