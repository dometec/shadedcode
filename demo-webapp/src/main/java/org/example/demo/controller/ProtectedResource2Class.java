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
@ApplicationRolesAllowed
@Path("services/protectedresource2")
public class ProtectedResource2Class {

	@AuthenticatedUser
	private DemoPrincipal user;

	@Inject
	public ProtectedResource2Class() {
	}

	@GET
	@Produces("text/plain")
	public String getString() {
		return "ProtectedResource2: Hello World, " + user.getUsername();
	}

}