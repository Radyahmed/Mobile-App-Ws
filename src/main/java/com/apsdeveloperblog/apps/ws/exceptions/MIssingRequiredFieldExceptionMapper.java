package com.apsdeveloperblog.apps.ws.exceptions;

import com.apsdeveloperblog.apps.ws.ui.model.response.ErrorMessage;
import com.apsdeveloperblog.apps.ws.ui.model.response.ErrorMessages;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MIssingRequiredFieldExceptionMapper implements ExceptionMapper<MissingRequiredFieldException> {
    
    @Override
    public Response toResponse(MissingRequiredFieldException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.MISSING_REQUIRED_FIELD.name(), " any ref you want user redirect to it to solve error ");
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
    
}
