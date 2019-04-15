/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controlador;

import com.saferus.backend.modelo.Mediador;
import com.saferus.backend.modelo.Segurado;
import com.saferus.backend.modelo.UtilizadorGenerico;
import com.saferus.backend.servico.UtilizadorServicoImpl;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lucasbrito
 */
@RestController
public class UtilizadorControlador {
    
    @Autowired
    private UtilizadorServicoImpl utilizadorServico;
    
    @RequestMapping(value = {"/consultar"}, method = RequestMethod.GET)
    public List<UtilizadorGenerico> consultarUtilizadores(){
        return utilizadorServico.consultarUtilizadores();
    }
    
    @RequestMapping(value = {"/consultar/{utilizador_id}"}, method = RequestMethod.GET)
    public UtilizadorGenerico consultarUtilizador(@PathVariable("utilizador_id") int idUtilizador){
        return utilizadorServico.consultarUtilizador(idUtilizador);
    }
    
    @RequestMapping(value = {"/consultar/segurados"}, method = RequestMethod.GET)
    public List<Segurado> consultarSegurados(){
        return utilizadorServico.consultarSegurados();
    }
    
    @RequestMapping(value = {"/consultar/segurado/{segurado_id}"}, method = RequestMethod.GET)
    public Segurado consultarSegurados(@PathVariable("segurado_id") int idSegurado){
        return utilizadorServico.consultarSegurado(idSegurado);
    }
    
    @RequestMapping(value = {"/consultar/mediadores"}, method = RequestMethod.GET)
    public List<Mediador> consultarMediadores(){
        return utilizadorServico.consultarMediadores();
    }
    
    @RequestMapping(value = {"/consultar/mediador/{mediador_id}"}, method = RequestMethod.GET)
    public Mediador consultarMediador(@PathVariable("mediador_id") int idMediador){
        return utilizadorServico.consultarMediador(idMediador);
    }
    
    @RequestMapping(value = {"/alterar/{utilizador_id}"}, method = RequestMethod.PUT)
    public UtilizadorGenerico alterarDados(@Valid @RequestBody UtilizadorGenerico ug, int idUtilizador){
        utilizadorServico.alterarDados(ug, idUtilizador);
        return ug;
    }
    
    @RequestMapping(value = {"/alterar/password/{utilizador_id}"}, method = RequestMethod.PUT)
    public String alterarPassword(@PathVariable("utilizador_id") int idUtilizador, @Valid @RequestBody String password, String novapassword){
        utilizadorServico.alterarPassword(idUtilizador, password, novapassword);
        return "Password alterada com sucesso";
    }
    
    
    
    
}
