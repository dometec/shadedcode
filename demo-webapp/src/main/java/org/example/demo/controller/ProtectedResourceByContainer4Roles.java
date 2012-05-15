package org.example.demo.controller;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Path("services/containterprotectedresources/4")
public class ProtectedResourceByContainer4Roles {

	@Context
	private SecurityContext securityContext;

	@Inject
	public ProtectedResourceByContainer4Roles() {
	}

	@GET
	@Produces("text/plain")
	@RolesAllowed("devinappclient")
	public String getString() {
		return "ProtectedResource4: Hello World dev, " + securityContext.getUserPrincipal().getName();
	}

}