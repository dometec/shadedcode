package org.example.demo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.example.demo.auth.AuthenticatedUser;
import org.example.demo.auth.DemoPrincipal;
import org.example.demo.auth.LoginCookieManager;
import org.example.demo.service.UserStore;
import org.example.demo.service.cluster.TokenStore;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Path("services/login")
public class LoginPage {

	@AuthenticatedUser
	private DemoPrincipal user;

	@Inject
	private UserStore userStore;

	@Inject
	private TokenStore tokenStore;

	@Inject
	private LoginCookieManager loginCookieManager;

	@InjectParam
	private HomePage homePage;

	@Inject
	public LoginPage() {
	}

	@GET
	@Produces("text/html; charset=utf-8")
	public Viewable getPage() {
		return new Viewable("/login.jsp");
	}

	@POST
	@Produces("text/html; charset=utf-8")
	public Response login(@FormParam("username") String username, @FormParam("password") String password) throws URISyntaxException {

		ResponseBuilder okResp = Response.seeOther(new URI("/services"));

		Map<String, String> model = new Hashtable<String, String>();
		model.put("message", "Utente o passoword non riconosciuti!");
		Viewable page = new Viewable("/login.jsp", model);
		ResponseBuilder koResp = Response.status(Status.UNAUTHORIZED).entity(page);

		return login(okResp, koResp, username, password).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginAjax(@FormParam("username") String username, @FormParam("password") String password) throws JSONException {

		ResponseBuilder okResp = Response.ok(new JSONObject("{login: ok}"));
		ResponseBuilder koResp = Response.status(Status.UNAUTHORIZED);

		return login(okResp, koResp, username, password).build();
	}

	private ResponseBuilder login(ResponseBuilder okResp, ResponseBuilder koResp, String username, String password) {

		if (user != null)
			return okResp;

		DemoPrincipal principal = userStore.authenticate(username, password);
		if (principal != null) {
			String token = tokenStore.createToken(principal);
			return loginCookieManager.login(okResp, token);
		}

		return koResp;

	}

}