/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.servico;

import com.saferus.backend.modelo.Mediador;
import com.saferus.backend.modelo.TipoConta;
import com.saferus.backend.modelo.UtilizadorGenerico;
import com.saferus.backend.repositorio.MediadorRepositorio;
import com.saferus.backend.repositorio.TipoContaRepositorio;
import com.saferus.backend.repositorio.UtilizadorGenericoRepositorio;
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
    private UtilizadorGenericoRepositorio ugRepositorio;

    @Autowired
    private MediadorRepositorio mediadorRepositorio;

    @Autowired
    private TipoContaRepositorio tpRepositorio;

    @Override
    public void registarUtilizador(UtilizadorGenerico novo) {
        novo.setPassword(bCryptPasswordEncoder.encode(novo.getPassword()));
        novo.setAtivo(0);
        TipoConta tp = tpRepositorio.findTipoContaById(2);
        novo.setTipoConta(new HashSet<TipoConta>(Arrays.asList(tp)));
        ugRepositorio.save(novo);
    }

    @Override
    public void registarMediador(Mediador novo) {
        novo.setPassword(bCryptPasswordEncoder.encode(novo.getPassword()));
        novo.setAtivo(0);
        TipoConta tp = tpRepositorio.findTipoContaById(4);
        novo.setTipoConta(new HashSet<TipoConta>(Arrays.asList(tp)));
        mediadorRepositorio.save(novo);
    }

    @Override
    public void eliminarUtilizador(int idUtilizadorGenerico) {
        UtilizadorGenerico ug = ugRepositorio.findUtilizadorGenericoById(idUtilizadorGenerico);
        if (ugRepositorio.findUtilizadorGenericoById(idUtilizadorGenerico) != null) {
            ugRepositorio.delete(ug);
        }
    }

    @Override
    public void eliminarMediador(int idMediador) {
        Mediador m = mediadorRepositorio.findMediadorById(idMediador);
        if (mediadorRepositorio.findMediadorById(idMediador) != null) {
            mediadorRepositorio.delete(m);
        }
    }

    @Override
    public boolean validarUtilizador(int idUtilizador) throws Exception {
        boolean validado = false;
        UtilizadorGenerico ug = ugRepositorio.findUtilizadorGenericoById(idUtilizador);
        if (ug.getAtivo() == 0) {
            ug.setAtivo(1);
            ugRepositorio.save(ug);
            validado = true;
        } else {
            throw new Exception("Utilizador já ativado");
        }
        return validado;
    }

    @Override
    public boolean validarMediador(int idMediador) throws Exception {
        boolean validado = false;
        Mediador m = mediadorRepositorio.findMediadorById(idMediador);
        if (m.getAtivo() == 0) {
            m.setAtivo(1);
            mediadorRepositorio.save(m);
            validado = true;
        } else {
            throw new Exception("Mediador já ativado");
        }
        return validado;
    }

    @Override
    public UtilizadorGenerico findUtilizadorGenericoByEmail(int id) {
        return ugRepositorio.findUtilizadorGenericoById(id);
    }

}
