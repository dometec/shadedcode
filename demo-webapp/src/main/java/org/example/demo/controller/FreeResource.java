package org.example.demo.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.example.demo.auth.AuthenticatedUser;
import org.example.demo.auth.DemoPrincipal;

import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Path("services/freeresource")
public class FreeResource {

	@AuthenticatedUser
	private DemoPrincipal user;

	@Inject
	public FreeResource() {
	}

	@GET
	@Produces("text/plain")
	public String getString() {

		if (user == null)
			return "Hello World, guest!";
		return "Hello World, " + user.getUsername() + "!";

	}

}