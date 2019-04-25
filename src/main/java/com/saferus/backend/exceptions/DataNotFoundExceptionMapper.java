/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.exceptions;

import com.saferus.backend.model.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author lucasbrito
 */
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
    
    @Override
    public Response toResponse(DataNotFoundException ex){
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 404);
        return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
    }
    
}
