/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controlador;

import com.saferus.backend.modelo.Mediador;
import com.saferus.backend.modelo.Utilizador;
import com.saferus.backend.modelo.UtilizadorGenerico;
import com.saferus.backend.servico.RegistoServico;
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
@RestController()
public class RegistoControlador {
    
    private RegistoServico registoServico;
    
    @RequestMapping(value = {"/registo/utilizador"}, method = RequestMethod.POST)
    public Utilizador registarUtilizador(@Valid @RequestBody UtilizadorGenerico ug){
        registoServico.registarUtilizador(ug);
        return ug;
    }
    
    @RequestMapping(value = {"/registo/mediador"}, method = RequestMethod.POST)
    public Mediador registarMediador(@Valid @RequestBody Mediador m){
        registoServico.registarMediador(m);
        return m;
    }
    
    @RequestMapping(value = {"/eliminar/{utilizador_id}"}, method = RequestMethod.DELETE)
    public void eliminarUtilizador(@PathVariable("utilizador_id") long idUtilizador){
        registoServico.eliminarUtilizador(idUtilizador);
    }
    
    @RequestMapping(value = {"/eliminar/{mediador_id}"}, method = RequestMethod.DELETE)
    public void eliminarMediador(@PathVariable("mediador_id") long idMediador){
        registoServico.eliminarMediador(idMediador);
    }
    
    @RequestMapping(value = {"/validar/{utilizador_id}"}, method = RequestMethod.PUT)
    public String validarUtilizador(@PathVariable("utilizador_id") long idUtilizador) throws Exception{
        registoServico.validarUtilizador(idUtilizador);
        return "Utilizador Validado Com Sucesso";
    }
    
    @RequestMapping(value = {"/validar/{mediador_id}"}, method = RequestMethod.PUT)
    public String validarMediador(@PathVariable("mediador_id") long idMediador) throws Exception{
        registoServico.validarMediador(idMediador);
        return "Mediador Validado Com Sucesso";
    }
}
