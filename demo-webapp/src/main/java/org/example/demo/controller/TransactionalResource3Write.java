package org.example.demo.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.example.demo.persistence.AbstractTransactionalResource;
import org.example.demo.persistence.aop.Transactional;
import org.example.demo.persistence.entities.DatabaseLog;

import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Transactional
@Path("services/transactionalresource3")
public class TransactionalResource3Write extends AbstractTransactionalResource {

	@Inject
	public TransactionalResource3Write() {
	}

	/*
	 * Non usare GET per quando si modifica lo stato interno del server!
	 */
	@GET
	@Produces("text/plain")
	public String get() {

		DatabaseLog databaseLog = new DatabaseLog();
		databaseLog.setCode(500);
		databaseLog.setReason("myReason");
		databaseLog.setMessage("myMessage");

		getSession().save(databaseLog);

		return "Inserted";

	}

}