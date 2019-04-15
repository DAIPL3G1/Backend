/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.controlador;

import com.saferus.backend.modelo.Conta;
import com.saferus.backend.modelo.Utilizador;
import com.saferus.backend.servico.UtilizadorServico;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lucasbrito
 */
@RestController
public class AutenticacaoControlador {
    
    @Autowired
    private UtilizadorServico utilizadorServico;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @ResponseBody
    public String MessagemCasa(Principal p) {
        Utilizador u = utilizadorServico.findUtilizadorByEmail(p.getName());
        return "Loggado com Sucesso Sr." + u.getNome();
    }

}
