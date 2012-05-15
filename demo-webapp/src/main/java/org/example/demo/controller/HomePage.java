package org.example.demo.controller;

import java.util.Hashtable;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.example.demo.auth.AuthenticatedUser;
import org.example.demo.auth.DemoPrincipal;
import org.example.demo.service.cluster.TokenStore;

import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Path("services")
public class HomePage {

	@AuthenticatedUser
	private DemoPrincipal user;

	@Inject
	private TokenStore tokenStore;

	@Inject
	public HomePage() {
	}

	@GET
	@Produces("text/html; charset=utf-8")
	public Viewable getHomePage() {

		Hashtable<String, String> model = new Hashtable<String, String>();

		if (user != null) {
			model.put("user", user.getUsername());
			model.put("expire", tokenStore.getExpireByToken(user.getUsername()));
		}

		return new Viewable("/index.jsp", model);
	}

}