/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.servico;

import com.saferus.backend.modelo.Utilizador;

/**
 *
 * @author lucasbrito
 */
public interface RegistoServico {
    
    public Utilizador findUtilizadorGenericoById(int id);
    public void registarUtilizador(Utilizador novo);
    public void registarMediador(Utilizador novo);
    public void eliminarUtilizador(int idUtilizadorGenerico);
    public void eliminarMediador(int idMediador);
    public boolean validarUtilizador(int idUtilizador) throws Exception;
    public boolean validarMediador(int idMediador) throws Exception;
    
}
