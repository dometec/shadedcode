package org.example.demo.auth;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotAuthenticatedExceptionMapper implements ExceptionMapper<NotAuthenticatedException> {

	public Response toResponse(NotAuthenticatedException exception) {
		return Response.status(Status.UNAUTHORIZED).entity("User not logged in!").build();
	}

}
