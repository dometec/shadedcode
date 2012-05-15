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
@Path("services/protectedresource1")
public class ProtectedResource1Method {

	@AuthenticatedUser
	private DemoPrincipal user;

	@Inject
	public ProtectedResource1Method() {
	}

	@GET
	@Produces("text/plain")
	@ApplicationRolesAllowed
	public String getString() {
		return "ProtectedResource1: Hello World, " + user.getUsername();
	}

}