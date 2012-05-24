package org.example.demo.auth;

/**
 * Response when resource required a role that logged user doesn't have.
 * 
 * @author dometec
 *
 */
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

	public Response toResponse(NotAuthorizedException exception) {
		return Response.status(Status.UNAUTHORIZED).entity("User not authorized!").build();
	}

}
