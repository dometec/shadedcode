package org.example.demo.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Path("services/none")
public class None {

	@Inject
	public None() {
	}

	@GET
	public Response getNone() {
		return Response.noContent().build();
	}

}