package org.example.demo.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

@Singleton
public class HibernateSessionService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private SessionFactory sessionFactory;

	public synchronized SessionFactory getSessionFactory() {

		if (sessionFactory != null)
			return sessionFactory;

		Properties extraProperties = new Properties();
		sessionFactory = new Configuration().addProperties(extraProperties).configure().buildSessionFactory();

		logger.info("Hibernate SessionFactory build successfully.");

		return sessionFactory;

	}

	public Session openSession(boolean readonly) {
		Session session = getSessionFactory().openSession();

		if (readonly) {
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					conn.setReadOnly(true);
				}
			});
			session.setFlushMode(FlushMode.MANUAL);
		} else {
			session.doWork(new Work() {
				public void execute(Connection conn) throws SQLException {
					conn.setReadOnly(false);
				}
			});
			session.setFlushMode(FlushMode.AUTO);
		}

		logger.trace("Open session " + session.hashCode() + " defReadOnly: " + readonly + ".");
		return session;
	}

	public void closeSession(Session session) {

		if (session != null) {

			logger.trace("Close session {}.", session.hashCode());

			if (session.isConnected()) {

				session.clear();
				Transaction t = session.getTransaction();

				if (t.isActive())
					t.rollback();
				session.flush();
			}

			if (session.isOpen())
				session.close();
		}
	}

	public synchronized void shutdown() {

		try {

			if (sessionFactory != null)
				sessionFactory.close();

			logger.info("Hibernate SessionFactory closed successfully.");

		} catch (Exception e) {
			logger.warn("Error shutting down hibernate session factory.", e);
		}

	}

}
