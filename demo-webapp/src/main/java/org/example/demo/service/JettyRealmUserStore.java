package org.example.demo.service;

import java.lang.reflect.Field;
import java.security.Principal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.DefaultUserIdentity;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.example.demo.auth.DemoPrincipal;

public class JettyRealmUserStore implements RealmUserStore {

	private LoginService loginService;

	@Inject
	public JettyRealmUserStore(@Named("ServletContext") ServletContext servletContext) {

		WebAppContext.Context context = ((org.eclipse.jetty.webapp.WebAppContext.Context) servletContext);
		ContextHandler contextHandler = context.getContextHandler();
		ConstraintSecurityHandler securityHandler = contextHandler
				.getChildHandlerByClass(org.eclipse.jetty.security.ConstraintSecurityHandler.class);
		loginService = securityHandler.getLoginService();
	}

	@Override
	public DemoPrincipal authenticate(String user, String password) {

		try {

			UserIdentity login = loginService.login(user, password);
			if (login == null)
				return null;

			Principal principal = login.getUserPrincipal();

			Field privateField = DefaultUserIdentity.class.getDeclaredField("_roles");
			privateField.setAccessible(true);
			String[] roles = (String[]) privateField.get(login);

			return new DemoPrincipal(principal, roles);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public DemoPrincipal toDemoPrincipal(Principal principal) {
		return new DemoPrincipal(principal);
	}

}
