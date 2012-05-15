package org.example.demo.persistence;

import org.example.demo.persistence.aop.NoTransactional;
import org.hibernate.Session;

public abstract class AbstractTransactionalResource {

	private Session session;

	@NoTransactional
	public void setSession(Session session) {
		this.session = session;
	}

	@NoTransactional
	public Session getSession() {
		return session;
	}

	@Override
	@NoTransactional
	protected void finalize() throws Throwable {
		super.finalize();
	}

}