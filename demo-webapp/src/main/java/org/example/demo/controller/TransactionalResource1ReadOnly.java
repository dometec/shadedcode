package org.example.demo.controller;

import java.util.List;

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
@Path("services/transactionalresource1")
public class TransactionalResource1ReadOnly extends AbstractTransactionalResource {

	@Inject
	public TransactionalResource1ReadOnly() {
	}

	@GET
	@Produces("text/plain")
	@SuppressWarnings("unchecked")
	public String get() {

		List<DatabaseLog> list = getSession().createCriteria(DatabaseLog.class).list();

		if (list.size() == 0)
			return "No record!";

		StringBuilder sb = new StringBuilder();
		for (DatabaseLog databaseLog : list) {
			sb.append(databaseLog.toString()).append('\n');
		}

		return sb.toString();
	}

}