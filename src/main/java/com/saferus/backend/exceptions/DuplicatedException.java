/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.exceptions;

/**
 *
 * @author lucasbrito
 */
public class DuplicatedException extends RuntimeException{
    
    public DuplicatedException(String message){
        super(message);
    }
    
}
