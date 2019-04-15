/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.servico;

import com.saferus.backend.modelo.TipoConta;
import com.saferus.backend.modelo.Utilizador;
import com.saferus.backend.repositorio.TipoContaRepositorio;
import com.saferus.backend.repositorio.UtilizadorRepositorio;
import java.util.Arrays;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author lucasbrito
 */
@Service("registoServico")
public class RegistoServicoImpl implements RegistoServico {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UtilizadorRepositorio utilizadorRepositorio;

    @Autowired
    private TipoContaRepositorio tpRepositorio;

    @Override
    public void registarUtilizador(Utilizador novo) {
        novo.setPassword(bCryptPasswordEncoder.encode(novo.getPassword()));
        novo.setAtivo(0);
        TipoConta tp = tpRepositorio.findTipoContaById(2);
        novo.setTipoConta(new HashSet<TipoConta>(Arrays.asList(tp)));
        novo.setDesignacao(tp.getDesignacao());
        utilizadorRepositorio.save(novo);
    }

    @Override
    public void registarMediador(Utilizador novo) {
        novo.setPassword(bCryptPasswordEncoder.encode(novo.getPassword()));
        novo.setAtivo(0);
        TipoConta tp = tpRepositorio.findTipoContaById(4);
        novo.setTipoConta(new HashSet<TipoConta>(Arrays.asList(tp)));
        novo.setDesignacao(tp.getDesignacao());
        utilizadorRepositorio.save(novo);
    }

    @Override
    public void eliminarUtilizador(int idUtilizadorGenerico) {
        Utilizador ug = utilizadorRepositorio.findUtilizadorGenericoById(idUtilizadorGenerico);
        if (utilizadorRepositorio.findUtilizadorGenericoById(idUtilizadorGenerico) != null) {
            utilizadorRepositorio.delete(ug);
        }
    }

    @Override
    public void eliminarMediador(int idMediador) {
        Utilizador m = utilizadorRepositorio.findMediadorById(idMediador);
        if (utilizadorRepositorio.findMediadorById(idMediador) != null) {
            utilizadorRepositorio.delete(m);
        }
    }

    @Override
    public boolean validarUtilizador(int idUtilizador) throws Exception {
        boolean validado = false;
        Utilizador ug = utilizadorRepositorio.findUtilizadorGenericoById(idUtilizador);
        if (ug.getAtivo() == 0) {
            ug.setAtivo(1);
            utilizadorRepositorio.save(ug);
            validado = true;
        } else {
            throw new Exception("Utilizador já ativado");
        }
        return validado;
    }

    @Override
    public boolean validarMediador(int idMediador) throws Exception {
        boolean validado = false;
        Utilizador m = utilizadorRepositorio.findMediadorById(idMediador);
        if (m.getAtivo() == 0) {
            m.setAtivo(1);
            utilizadorRepositorio.save(m);
            validado = true;
        } else {
            throw new Exception("Mediador já ativado");
        }
        return validado;
    }

    @Override
    public Utilizador findUtilizadorGenericoById(int id) {
        return utilizadorRepositorio.findUtilizadorGenericoById(id);
    }

}
