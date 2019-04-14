/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.servico;

import com.saferus.backend.modelo.Mediador;
import com.saferus.backend.modelo.UtilizadorGenerico;
import com.saferus.backend.repositorio.MediadorRepositorio;
import com.saferus.backend.repositorio.UtilizadorGenericoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author lucasbrito
 */
@Service("registoServico")
public class RegistoServico {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UtilizadorGenericoRepositorio ugRepositorio;
    
    private MediadorRepositorio mediadorRepositorio;

    public long registarUtilizador(UtilizadorGenerico novo) {
        novo.setPassword(bCryptPasswordEncoder.encode(novo.getPassword()));
        novo.setAtivo(1);
        ugRepositorio.criar(novo);
        return novo.getId();
    }
    
    public long registarMediador(Mediador novo) {
        novo.setPassword(bCryptPasswordEncoder.encode(novo.getPassword()));
        novo.setAtivo(0);
        mediadorRepositorio.criar(novo);
        return novo.getId();
    }
    
    public boolean eliminarUtilizador(long idUtilizadorGenerico){
        boolean valido = false;
        if(ugRepositorio.ler(idUtilizadorGenerico) != null){
            ugRepositorio.remover(idUtilizadorGenerico);
            valido = true;
        }
        return valido;
    }
    
    public boolean eliminarMediador(long idMediador){
        boolean valido = false;
        if(mediadorRepositorio.ler(idMediador) != null){
            mediadorRepositorio.remover(idMediador);
            valido = true;
        }
        return valido;
    }
    
    public boolean validarUtilizador(long idUtilizador) throws Exception{
        boolean validado = false;
        UtilizadorGenerico ug = ugRepositorio.ler(idUtilizador);
        if(ug.getAtivo() == 0){
            ug.setAtivo(1);
            validado = true;
        }
        else{
            throw new Exception("Utilizador já ativado");
        }
        return validado;
    }
    
    public boolean validarMediador(long idMediador) throws Exception{
        boolean validado = false;
        Mediador m = mediadorRepositorio.ler(idMediador);
        if(m.getAtivo() == 0){
            m.setAtivo(1);
            validado = true;
        }
        else{
            throw new Exception("Mediador já ativado");
        }
        return validado;
    }

}
