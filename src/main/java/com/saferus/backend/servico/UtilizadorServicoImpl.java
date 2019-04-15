/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.servico;

import com.saferus.backend.modelo.Mediador;
import com.saferus.backend.modelo.Segurado;
import com.saferus.backend.modelo.UtilizadorGenerico;
import com.saferus.backend.repositorio.MediadorRepositorio;
import com.saferus.backend.repositorio.SeguradoRepositorio;
import com.saferus.backend.repositorio.UtilizadorGenericoRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author lucasbrito
 */
@Service("utilizadorServico")
public class UtilizadorServicoImpl implements UtilizadorServico {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UtilizadorGenericoRepositorio ugRepositorio;

    @Autowired
    private SeguradoRepositorio seguradoRepositorio;

    @Autowired
    private MediadorRepositorio mediadorRepositorio;

    @Override
    public List<UtilizadorGenerico> consultarUtilizadores() {
        return ugRepositorio.findAll();
    }

    @Override
    public UtilizadorGenerico consultarUtilizador(int idUtilizador) {
        UtilizadorGenerico ug = ugRepositorio.findUtilizadorGenericoById(idUtilizador);
        return ug;
    }

    @Override
    public UtilizadorGenerico alterarDados(UtilizadorGenerico ug, int idUtilizador) {
        ug.setId(idUtilizador);
        ugRepositorio.save(ug);
        return ug;
    }

    @Override
    public void alterarPassword(int idUtilizador, String novapassword, String antigapassword) {
        UtilizadorGenerico ug = ugRepositorio.findUtilizadorGenericoById(idUtilizador);
        if (ug.getPassword() == bCryptPasswordEncoder.encode(antigapassword)) {
            ug.setPassword(bCryptPasswordEncoder.encode(novapassword));
            ug.setId(idUtilizador);
        }
        ugRepositorio.save(ug);
    }

    @Override
    public List<Segurado> consultarSegurados() {
        return seguradoRepositorio.findAll();
    }

    @Override
    public Segurado consultarSegurado(int idSegurado) {
        Segurado s = seguradoRepositorio.findSeguradoById(idSegurado);
        return s;
    }

    @Override
    public List<Mediador> consultarMediadores() {
        return mediadorRepositorio.findAll();
    }

    @Override
    public Mediador consultarMediador(int idMediador) {
        Mediador m = mediadorRepositorio.findMediadorById(idMediador);
        return m;
    }

}
