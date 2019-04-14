/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.servico;

import com.saferus.backend.modelo.Mediador;
import com.saferus.backend.modelo.Segurado;
import com.saferus.backend.modelo.UtilizadorGenerico;
import com.saferus.backend.modelo.Vinculacao;
import com.saferus.backend.repositorio.MediadorRepositorio;
import com.saferus.backend.repositorio.SeguradoRepositorio;
import com.saferus.backend.repositorio.UtilizadorGenericoRepositorio;
import com.saferus.backend.repositorio.VinculacaoRepositorio;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lucasbrito
 */
@Service("vinculacaoServico")
public class VinculacaoServico {

    private MediadorRepositorio mediadorRepositorio;

    private UtilizadorGenericoRepositorio ugRepositorio;

    private SeguradoRepositorio seguradoRepositorio;

    private VinculacaoRepositorio vinculacaoRepositorio;

    public UUID criarVinculacaoPorValidar(long idMediador, long idUtilizadorGenerico) throws Exception {

        Vinculacao nova = new Vinculacao();
        nova.setDataPedido(Instant.now());
        Mediador m = mediadorRepositorio.ler(idMediador);
        UtilizadorGenerico u = ugRepositorio.ler(idUtilizadorGenerico);
        if (m == null || u == null) {
            throw new Exception("Erro");
        }
        Segurado segurado = new Segurado();
        segurado.setUtilizadorGenerico(u);
        seguradoRepositorio.criar(segurado);
        nova.setMediador(m);
        nova.setSegurado(segurado);
        UUID uniqueKey = UUID.randomUUID();
        nova.setCodigo(uniqueKey);
        nova.setValida(0);
        vinculacaoRepositorio.criar(nova);

        return nova.getCodigo();

    }

    public Vinculacao validarVinculacao(long idVinculacao) throws Exception {
        Vinculacao v = vinculacaoRepositorio.ler(idVinculacao);
        if (v.getValida() == 0) {
            v.setValida(1);
        } else {
            throw new Exception("Vinculacao já foi Ativada!");
        }
        return v;
    }

    public boolean desvincularSegurado(long idSegurado) throws Exception {
        boolean desvinculado = false;
        Vinculacao v = seguradoRepositorio.ler(idSegurado).getVinculacao();
        if (v != null) {
            vinculacaoRepositorio.remover(v.getId());
            seguradoRepositorio.remover(idSegurado);
            desvinculado = true;
        }
        else{
            throw new Exception("Ocorreu erro a desvincular");
        }
        return desvinculado;
    }
    
    public List<Vinculacao> consularVinculacoes(){
        return vinculacaoRepositorio.lerTodos();
    }
    
    public Vinculacao consultarVinculacao(long idSegurado){
        Vinculacao v = vinculacaoRepositorio.ler(seguradoRepositorio.ler(idSegurado).getVinculacao().getId());
        return v;
    }

}
