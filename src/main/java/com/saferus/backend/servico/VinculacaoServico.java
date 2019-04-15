/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.servico;

import com.saferus.backend.modelo.Vinculacao;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author lucasbrito
 */
public interface VinculacaoServico {
    
    public UUID criarVinculacaoPorValidar(int idMediador, int idUtilizadorGenerico) throws Exception;
    public Vinculacao validarVinculacao(int idVinculacao) throws Exception;
    public void desvincularSegurado(int idSegurado) throws Exception;
    public List<Vinculacao> consularVinculacoes();
    public Vinculacao consultarVinculacao(int idSegurado);
    
}
