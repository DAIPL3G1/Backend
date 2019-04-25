/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.exceptions;

import com.saferus.backend.model.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author lucasbrito
 */
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException>{
    
    @Override
    public Response toResponse(BadRequestException ex){
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 400);
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
    
}
