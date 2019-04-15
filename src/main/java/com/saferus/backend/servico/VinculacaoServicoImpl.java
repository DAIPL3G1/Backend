/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.servico;

import com.saferus.backend.modelo.TipoConta;
import com.saferus.backend.modelo.Utilizador;
import com.saferus.backend.modelo.Vinculacao;
import com.saferus.backend.repositorio.TipoContaRepositorio;
import com.saferus.backend.repositorio.UtilizadorRepositorio;
import com.saferus.backend.repositorio.VinculacaoRepositorio;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lucasbrito
 */
@Service("vinculacaoServico")
public class VinculacaoServicoImpl implements VinculacaoServico {

    @Autowired
    private UtilizadorRepositorio utilizadorRepositorio;

    @Autowired
    private VinculacaoRepositorio vinculacaoRepositorio;

    @Autowired
    private TipoContaRepositorio tpRepositorio;

    @Override
    public UUID criarVinculacaoPorValidar(int idMediador, int idUtilizadorGenerico) throws Exception {
        Utilizador m = utilizadorRepositorio.findMediadorById(idMediador);
        Utilizador u = utilizadorRepositorio.findUtilizadorGenericoById(idUtilizadorGenerico);
        if (!(m.getDesignacao().equals("MEDIADOR")) && !(u.getDesignacao().equals("GENERICO"))) {
            throw new Exception("ERRO");
        }
        TipoConta tp = tpRepositorio.findTipoContaById(3);
        u.setTipoConta(new HashSet<TipoConta>(Arrays.asList(tp)));
        u.setDesignacao(tp.getDesignacao());
        Vinculacao nova = new Vinculacao();
        nova.setDataPedido(Instant.now());
        nova.setMediador(m);
        nova.setSegurado(u);
        UUID uniqueKey = UUID.randomUUID();
        nova.setCodigo(uniqueKey);
        nova.setValida(0);
        vinculacaoRepositorio.save(nova);
        return nova.getCodigo();

    }

    @Override
    public Vinculacao validarVinculacao(int idVinculacao) throws Exception {
        Vinculacao v = vinculacaoRepositorio.findVinculacaoById(idVinculacao);
        if (v.getValida() == 0) {
            v.setValida(1);
        } else {
            throw new Exception("Vinculacao já foi Ativada!");
        }
        return v;
    }

    @Override
    public void desvincularSegurado(int idSegurado) throws Exception {
        Utilizador s = utilizadorRepositorio.findSeguradoById(idSegurado);
        Vinculacao v = vinculacaoRepositorio.findVinculacaoBySegurado(s);
        if (v != null) {
            vinculacaoRepositorio.delete(v);
            s.setDesignacao("GENERICO");
            TipoConta tp = tpRepositorio.findTipoContaById(2);
            s.setTipoConta(new HashSet<TipoConta>(Arrays.asList(tp)));
            utilizadorRepositorio.save(s);
        } else {
            throw new Exception("Ocorreu erro a desvincular");
        }
    }

    @Override
    public List<Vinculacao> consularVinculacoes() {
        return vinculacaoRepositorio.findAll();
    }

    @Override
    public Vinculacao consultarVinculacao(int idSegurado) {
        Utilizador s = utilizadorRepositorio.findSeguradoById(idSegurado);
        Vinculacao v = vinculacaoRepositorio.findVinculacaoBySegurado(s);
        return v;
    }

}
