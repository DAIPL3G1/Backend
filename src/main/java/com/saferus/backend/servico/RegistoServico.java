/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.servico;

import com.saferus.backend.modelo.Mediador;
import com.saferus.backend.modelo.UtilizadorGenerico;

/**
 *
 * @author lucasbrito
 */
public interface RegistoServico {
    
    public UtilizadorGenerico findUtilizadorGenericoByEmail(int id);
    public void registarUtilizador(UtilizadorGenerico novo);
    public void registarMediador(Mediador novo);
    public void eliminarUtilizador(int idUtilizadorGenerico);
    public void eliminarMediador(int idMediador);
    public boolean validarUtilizador(int idUtilizador) throws Exception;
    public boolean validarMediador(int idMediador) throws Exception;
    
}
