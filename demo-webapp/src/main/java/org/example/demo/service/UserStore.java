package org.example.demo.service;

import java.security.Principal;
import java.util.Hashtable;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.example.demo.auth.DemoPrincipal;

@Singleton
public class UserStore {

	@Inject
	private RealmUserStore realmUserStore;

	private Map<String, String> userdb;

	public UserStore() {

		userdb = new Hashtable<String, String>();
		userdb.put("user1", "pass1");
		userdb.put("user2", "pass2");
		userdb.put("user3", "pass3");

	}

	public Principal getPrincipalFromUsername(String username) {
		return new DemoPrincipal(username);
	}

	public DemoPrincipal authenticate(String username, String password) {

		if (username == null)
			return null;

		if (userdb.containsKey(username) && userdb.get(username).equals(password))
			return new DemoPrincipal(username);

		return realmUserStore.authenticate(username, password);

	}

	public boolean isUserInRole(DemoPrincipal principal, String[] rolesRequired) {

		if (rolesRequired.length == 1 && "*".equals(rolesRequired[0]))
			return true;

		for (String role : rolesRequired) {
			if (principal.hasRole(role))
				return true;
		}

		return false;

	}

	public DemoPrincipal toDemoPrincipal(Principal principal) {
		return realmUserStore.toDemoPrincipal(principal);
	}

}
