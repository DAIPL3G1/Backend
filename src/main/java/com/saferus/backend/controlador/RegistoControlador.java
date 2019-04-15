/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controlador;

import com.saferus.backend.modelo.Utilizador;
import com.saferus.backend.servico.RegistoServicoImpl;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RegistoServicoImpl registoServico;

    @RequestMapping(value = {"/registo/utilizador"}, method = RequestMethod.POST)
    public Utilizador registarUtilizador(@Valid @RequestBody Utilizador ug) throws Exception {
        Utilizador result = registoServico.findUtilizadorGenericoById(ug.getId());
        if (result != null) {
            throw new Exception("Utilizador já existente");
        }
        registoServico.registarUtilizador(ug);
        return ug;
    }

    @RequestMapping(value = {"/registo/mediador"}, method = RequestMethod.POST)
    public Utilizador registarMediador(@Valid @RequestBody Utilizador m) {
        registoServico.registarMediador(m);
        return m;
    }

    @RequestMapping(value = {"/acesso_negado"}, method = RequestMethod.GET)
    public String acessoNegado() {
        return "Acesso Negado! Tente Novamente mais tarde!";
    }

    @RequestMapping(value = {"/eliminar/utilizador/{utilizador_id}"}, method = RequestMethod.DELETE)
    public void eliminarUtilizador(@PathVariable("utilizador_id") int idUtilizador) {
        registoServico.eliminarUtilizador(idUtilizador);
    }

    @RequestMapping(value = {"/eliminar/mediador/{mediador_id}"}, method = RequestMethod.DELETE)
    public void eliminarMediador(@PathVariable("mediador_id") int idMediador) {
        registoServico.eliminarMediador(idMediador);
    }

    @RequestMapping(value = {"/validar/utilizador/{utilizador_id}"}, method = RequestMethod.PUT)
    public String validarUtilizador(@PathVariable("utilizador_id") int idUtilizador) throws Exception {
        registoServico.validarUtilizador(idUtilizador);
        return "Utilizador Validado Com Sucesso";
    }

    @RequestMapping(value = {"/validar/mediador/{mediador_id}"}, method = RequestMethod.PUT)
    public String validarMediador(@PathVariable("mediador_id") int idMediador) throws Exception {
        registoServico.validarMediador(idMediador);
        return "Mediador Validado Com Sucesso";
    }
}
