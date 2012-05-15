package org.example.demo.auth;

import java.io.Serializable;
import java.security.Principal;

public class DemoPrincipal implements Principal, Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String[] roles;

	public DemoPrincipal(String username, String[] roles) {
		this.username = username;
		this.roles = roles;
	}

	public DemoPrincipal(String username) {
		this(username, null);
	}

	public DemoPrincipal(Principal principal) {
		this(principal.getName(), null);
	}

	public DemoPrincipal(Principal principal, String[] roles) {
		this(principal.getName(), roles);
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String getName() {
		return username;
	}

	public boolean hasRole(String role) {

		if ("*".equals(role))
			return true;

		if (role == null)

			return false;

		for (String i : roles) {
			if (role.equals(i))
				return true;
		}

		return false;
	}

}
