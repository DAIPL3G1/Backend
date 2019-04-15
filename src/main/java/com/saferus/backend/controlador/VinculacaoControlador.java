/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controlador;

import com.saferus.backend.modelo.Vinculacao;
import com.saferus.backend.servico.VinculacaoServicoImpl;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lucasbrito
 */
@RestController()
@RequestMapping("/vinculacao")
public class VinculacaoControlador {
    
    @Autowired
    private VinculacaoServicoImpl vinculacaoServico;
    
    @RequestMapping(value = {"/{mediador_id}/{utilizador_id}"}, method = RequestMethod.POST)
    public UUID pedirVinculacao(@PathVariable("mediador_id") int idMediador, @PathVariable("utilizador_id") int idUtilizador) throws Exception{
        return vinculacaoServico.criarVinculacaoPorValidar(idMediador, idUtilizador);
    }
    
    @RequestMapping(value = {"/validar/{vinculacao_id}"}, method = RequestMethod.PUT)
    public String validarVinculacao(@PathVariable("vinculacao_id") int idVinculacao) throws Exception{
        vinculacaoServico.validarVinculacao(idVinculacao);
        return "Vinculacao Validada Com Sucesso";
    }
    
    @RequestMapping(value = {"/eliminar/{segurado_id}"}, method = RequestMethod.PUT)
    public String desvincularSegurado(@PathVariable("segurado_id") int idSegurado) throws Exception{
        vinculacaoServico.desvincularSegurado(idSegurado);
        return "Segurado desvinculado com Sucesso";
    }
    
    @RequestMapping(value = {"/consultar"}, method = RequestMethod.GET)
    public List<Vinculacao> consultarVinculacoes(){
        return vinculacaoServico.consularVinculacoes();
    }
    
    @RequestMapping(value = {"/consultar/{segurado_id}"}, method = RequestMethod.GET)
    public Vinculacao consultarVinculacoes(@PathVariable("segurado_id") int idSegurado){
        return vinculacaoServico.consultarVinculacao(idSegurado);
    }
    
    
}
