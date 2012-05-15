package org.example.demo.controller;

import java.net.URISyntaxException;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.example.demo.auth.AuthenticatedUser;
import org.example.demo.auth.DemoPrincipal;
import org.example.demo.auth.aop.ApplicationRolesAllowed;
import org.example.demo.service.AccountStore;

import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Path("services/balance")
public class Balance {

	@AuthenticatedUser
	private DemoPrincipal user;
	
	@Inject
	private AccountStore acocuntStore;

	@Inject
	public Balance() {
	}

	@GET
	@ApplicationRolesAllowed
	@Produces("text/html; charset=utf-8")
	public Response getAccountBalance() throws URISyntaxException {
		Map<String, Integer> accounts = acocuntStore.getAll();
		Viewable viewable = new Viewable("/balance.jsp", accounts);
		return Response.ok(viewable).build();
	}

}