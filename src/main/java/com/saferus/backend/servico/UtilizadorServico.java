/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.servico;

import com.saferus.backend.modelo.Mediador;
import com.saferus.backend.modelo.Segurado;
import com.saferus.backend.modelo.UtilizadorGenerico;
import java.util.List;

/**
 *
 * @author lucasbrito
 */
public interface UtilizadorServico {
    
    public List<UtilizadorGenerico> consultarUtilizadores();
    public UtilizadorGenerico consultarUtilizador(int idUtilizador);
    public UtilizadorGenerico alterarDados(UtilizadorGenerico ug, int idUtilizador);
    public void alterarPassword(int idUtilizador, String novapassword, String antigapassword);
    public List<Segurado> consultarSegurados();
    public Segurado consultarSegurado(int idSegurado);
    public List<Mediador> consultarMediadores();
    public Mediador consultarMediador(int idMediador);
    
    
}
