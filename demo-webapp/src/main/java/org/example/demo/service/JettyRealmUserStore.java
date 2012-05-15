package org.example.demo.service;

import java.security.Principal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.example.demo.auth.DemoPrincipal;

public class JettyRealmUserStore implements RealmUserStore {

	@Inject
	public JettyRealmUserStore(@Named("ServletContext") ServletContext servletContext) {

	}

	@Override
	public Principal authenticate(String user, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DemoPrincipal toDemoPrincipal(Principal principal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getRoles(Principal principal) {
		// TODO Auto-generated method stub
		return null;
	}

}
