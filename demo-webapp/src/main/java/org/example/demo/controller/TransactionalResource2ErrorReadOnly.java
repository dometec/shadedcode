package org.example.demo.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.example.demo.persistence.AbstractTransactionalResource;
import org.example.demo.persistence.aop.TransactionType;
import org.example.demo.persistence.aop.Transactional;
import org.example.demo.persistence.entities.DatabaseLog;

import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Transactional(TransactionType.ReadOnly)
@Path("services/transactionalresource2")
public class TransactionalResource2ErrorReadOnly extends AbstractTransactionalResource {

	@Inject
	public TransactionalResource2ErrorReadOnly() {
	}

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