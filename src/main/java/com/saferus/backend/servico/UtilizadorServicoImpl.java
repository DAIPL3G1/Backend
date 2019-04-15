/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.servico;

import com.saferus.backend.modelo.Utilizador;
import com.saferus.backend.repositorio.UtilizadorRepositorio;
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
    private UtilizadorRepositorio utilizadorRepositorio;
    
    @Override
    public List<Utilizador> consultarUtilizadores() {
        List<Utilizador> utilizadoresGenericos = null;
        for(Utilizador u : utilizadorRepositorio.findAll()){
            if(u.equals("GENERICO")){
                utilizadoresGenericos.add(u);
            }
        }
        return utilizadoresGenericos;
    }

    @Override
    public Utilizador consultarUtilizador(int idUtilizador) {
        Utilizador ug = utilizadorRepositorio.findUtilizadorGenericoById(idUtilizador);
        return ug;
    }
    
    @Override
    public Utilizador findUtilizadorByEmail(String email){
        Utilizador u = utilizadorRepositorio.findUtilizadorByEmail(email);
        return u;
    }

    @Override
    public Utilizador alterarDados(Utilizador ug, int idUtilizador) {
        ug.setId(idUtilizador);
        utilizadorRepositorio.save(ug);
        return ug;
    }

    @Override
    public void alterarPassword(int idUtilizador, String novapassword, String antigapassword) {
        Utilizador ug = utilizadorRepositorio.findUtilizadorGenericoById(idUtilizador);
        if (ug.getPassword() == bCryptPasswordEncoder.encode(antigapassword)) {
            ug.setPassword(bCryptPasswordEncoder.encode(novapassword));
            ug.setId(idUtilizador);
        }
        utilizadorRepositorio.save(ug);
    }

    @Override
    public List<Utilizador> consultarSegurados() {
        List<Utilizador> segurados = null;
        for(Utilizador u : utilizadorRepositorio.findAll()){
            if(u.getDesignacao().equals("SEGURADO")){
                segurados.add(u);
            }
        }
        return segurados;
    }

    @Override
    public Utilizador consultarSegurado(int idSegurado) {
        Utilizador s = utilizadorRepositorio.findSeguradoById(idSegurado);
        return s;
    }

    @Override
    public List<Utilizador> consultarMediadores() {
         List<Utilizador> utilizadoresGenericos = null;
        for(Utilizador u : utilizadorRepositorio.findAll()){
            if(u.getDesignacao().equals("MEDIADOR")){
                utilizadoresGenericos.add(u);
            }
        }
        return utilizadoresGenericos;
    }

    @Override
    public Utilizador consultarMediador(int idMediador) {
        Utilizador m = utilizadorRepositorio.findMediadorById(idMediador);
        return m;
    }

}
