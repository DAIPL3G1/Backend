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
public class DuplicatedExceptionMapper implements ExceptionMapper<DuplicatedException>{
    
    @Override
    public Response toResponse(DuplicatedException ex){
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 409);
        return Response.status(Response.Status.CONFLICT).entity(errorMessage).build();
    }
    
}
