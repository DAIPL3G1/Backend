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
public class UtilizadorServico {
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    private UtilizadorGenericoRepositorio ugRepositorio;
    
    private SeguradoRepositorio seguradoRepositorio;
    
    private MediadorRepositorio mediadorRepositorio;
    
    public List<UtilizadorGenerico> consultarUtilizadores(){
        return ugRepositorio.lerTodos();
    }
    
    public UtilizadorGenerico consultarUtilizador(long idUtilizador){
        UtilizadorGenerico ug = ugRepositorio.ler(idUtilizador);
        return ug;
    }
    
    public UtilizadorGenerico alterarDados(UtilizadorGenerico ug, long idUtilizador){
        ugRepositorio.alterar(ug, idUtilizador);
        return ug;
    }
    
    public void alterarPassword(long idUtilizador, String novapassword, String antigapassword){
        UtilizadorGenerico ug = ugRepositorio.ler(idUtilizador);
        if(ug.getPassword() == bCryptPasswordEncoder.encode(antigapassword)){
            ug.setPassword(bCryptPasswordEncoder.encode(novapassword));
        }
        ugRepositorio.alterar(ug, idUtilizador);
    }
    
    public List<Segurado> consultarSegurados(){
        return seguradoRepositorio.lerTodos();
    }
    
    public Segurado consultarSegurado(long idSegurado){
        Segurado s = seguradoRepositorio.ler(idSegurado);
        return s;
    }
    
    public List<Mediador> consultarMediadores(){
        return mediadorRepositorio.lerTodos();
    }
    
    public Mediador consultarMediador(long idMediador){
        Mediador m = mediadorRepositorio.ler(idMediador);
        return m;
    }
    
}
