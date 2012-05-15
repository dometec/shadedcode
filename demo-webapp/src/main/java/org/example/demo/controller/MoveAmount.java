package org.example.demo.controller;

import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.example.demo.aop.UniqueCallOnCluster;
import org.example.demo.service.AccountStore;

import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Path("services/moveamount")
public class MoveAmount {

	@Inject
	private AccountStore accountStore;

	@Inject
	public MoveAmount() {
	}

	@GET
	@Produces("text/html; charset=utf-8")
	@UniqueCallOnCluster
	public Response getAccountBalance(@QueryParam("fromuser") String fromuser, @QueryParam("touser") String tomuser,
			@QueryParam("amount") Integer amount) throws URISyntaxException {

		accountStore.decreaseAmount(fromuser, amount);
		accountStore.increaseAmount(tomuser, amount);

		return Response.status(Status.NO_CONTENT).build();
	}

}