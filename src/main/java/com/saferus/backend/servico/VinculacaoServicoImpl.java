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
public class VinculacaoServicoImpl implements VinculacaoServico {

    @Autowired
    private MediadorRepositorio mediadorRepositorio;

    @Autowired
    private UtilizadorGenericoRepositorio ugRepositorio;

    @Autowired
    private SeguradoRepositorio seguradoRepositorio;

    @Autowired
    private VinculacaoRepositorio vinculacaoRepositorio;

    @Override
    public UUID criarVinculacaoPorValidar(int idMediador, int idUtilizadorGenerico) throws Exception {
        Mediador m = mediadorRepositorio.findMediadorById(idMediador);
        UtilizadorGenerico u = ugRepositorio.findUtilizadorGenericoById(idUtilizadorGenerico);
        Segurado segurado = new Segurado();
        segurado.setUtilizadorGenerico(u);
        if (m == null || u == null) {
            throw new Exception("Erro");
        }
        Vinculacao nova = new Vinculacao();
        nova.setDataPedido(Instant.now());
        nova.setMediador(m);
        segurado.setUtilizadorGenerico(u);
        seguradoRepositorio.save(segurado);
        nova.setSegurado(segurado);
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
    public boolean desvincularSegurado(int idSegurado) throws Exception {
        
        Segurado s = seguradoRepositorio.findSeguradoById(idSegurado);
        if (v != null) {
            vinculacaoRepositorio.delete(v);
            seguradoRepositorio.delete(s);
            desvinculado = true;
        } else {
            throw new Exception("Ocorreu erro a desvincular");
        }
        return desvinculado;
    }

    @Override
    public List<Vinculacao> consularVinculacoes() {
        return vinculacaoRepositorio.findAll();
    }

    @Override
    public Vinculacao consultarVinculacao(int idSegurado) {
        Vinculacao v = vinculacaoRepositorio.findVinculacaoById(seguradoRepositorio.findSeguradoById(idSegurado).getVinculacao().getId());
        return v;
    }

}
