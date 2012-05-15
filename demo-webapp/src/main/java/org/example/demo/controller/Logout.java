package org.example.demo.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.example.demo.auth.LoginCookieManager;
import org.example.demo.service.cluster.TokenStore;

import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Path("services/logout")
public class Logout {

	@Inject
	private TokenStore tokenStore;

	@Inject
	private LoginCookieManager loginCookieManager;

	@Inject
	public Logout() {
	}

	@GET
	@Produces("text/html; charset=utf-8")
	public Response logout() throws URISyntaxException {
		ResponseBuilder page = Response.seeOther(new URI("/services"));
		page = loginCookieManager.logout(page);
		return page.build();
	}

}