/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.servico;

import com.saferus.backend.modelo.Utilizador;
import java.util.List;

/**
 *
 * @author lucasbrito
 */
public interface UtilizadorServico {
    
    public List<Utilizador> consultarUtilizadores();
    public Utilizador consultarUtilizador(int idUtilizador);
    public Utilizador alterarDados(Utilizador ug, int idUtilizador);
    public void alterarPassword(int idUtilizador, String novapassword, String antigapassword);
    public List<Utilizador> consultarSegurados();
    public Utilizador consultarSegurado(int idSegurado);
    public List<Utilizador> consultarMediadores();
    public Utilizador consultarMediador(int idMediador);
    public Utilizador findUtilizadorByEmail(String email);
    
    
}
