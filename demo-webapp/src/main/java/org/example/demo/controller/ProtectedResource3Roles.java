package org.example.demo.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.example.demo.auth.AuthenticatedUser;
import org.example.demo.auth.DemoPrincipal;
import org.example.demo.auth.aop.ApplicationRolesAllowed;

import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Path("services/protectedresource3")
public class ProtectedResource3Roles {

	@AuthenticatedUser
	private DemoPrincipal user;

	@Inject
	public ProtectedResource3Roles() {
	}

	@GET
	@Produces("text/plain")
	@ApplicationRolesAllowed("devinappclient")
	public String getString() {
		return "ProtectedResource3: Hello World devinappclient, " + user.getUsername();
	}

}