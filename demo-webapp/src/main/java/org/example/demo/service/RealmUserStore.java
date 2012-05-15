package org.example.demo.service;

import java.security.Principal;

import org.example.demo.auth.DemoPrincipal;

public interface RealmUserStore {

	Principal authenticate(String user, String password);

	DemoPrincipal toDemoPrincipal(Principal principal);

	String[] getRoles(Principal principal);

}