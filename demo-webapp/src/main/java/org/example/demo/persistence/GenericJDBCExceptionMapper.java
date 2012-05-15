package org.example.demo.persistence;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.exception.GenericJDBCException;

@Provider
public class GenericJDBCExceptionMapper implements ExceptionMapper<GenericJDBCException> {

	public Response toResponse(GenericJDBCException exception) {
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Errore db interno, riprova più tardi!").build();
	}

}
