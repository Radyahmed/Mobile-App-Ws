/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apsdeveloperblog.apps.ws.exceptions;

import com.apsdeveloperblog.apps.ws.ui.model.response.ErrorMessage;
import com.apsdeveloperblog.apps.ws.ui.model.response.ErrorMessages;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Rady Ahmed
 */
@Provider
public class NoRecordFoundExceptionMapper implements ExceptionMapper<NoRecordFoundException> {

    @Override
    public Response toResponse(NoRecordFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.No_RECORD_FOUND.name(), " any ref you want user redirect to it to solve error ");
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
