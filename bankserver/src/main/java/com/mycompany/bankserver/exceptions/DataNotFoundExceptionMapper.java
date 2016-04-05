
package com.mycompany.bankserver.exceptions;

import com.mycompany.bankserver.model.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author M
 */
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
    @Override
    public Response toResponse(DataNotFoundException ex){
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),404);
        return Response.status(Status.NOT_FOUND)
                .entity(errorMessage)
                .build();
    }
    
}
