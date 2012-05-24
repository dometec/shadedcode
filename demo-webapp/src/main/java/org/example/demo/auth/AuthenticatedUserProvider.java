package org.example.demo.auth;

import java.security.Principal;

import javax.ws.rs.ext.Provider;

import org.example.demo.service.UserStore;

import com.google.inject.Inject;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.api.core.HttpRequestContext;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.PerRequestTypeInjectableProvider;

/**
 *
 * This provider inject into field of resources class annotated with @AuthenticatedUser 
 * the DemoPrincipal represent the current users. Null if nobody is logged 
 * 
 * @author dometec
 *
 */
@Provider
public class AuthenticatedUserProvider extends PerRequestTypeInjectableProvider<AuthenticatedUser, DemoPrincipal> {

	@Inject
	private InjectableUser injectableUser;

	public AuthenticatedUserProvider() {
		super(DemoPrincipal.class);
	}

	@Override
	public Injectable<DemoPrincipal> getInjectable(ComponentContext ic, AuthenticatedUser a) {
		return injectableUser;
	}

}

class InjectableUser extends AbstractHttpContextInjectable<DemoPrincipal> {

	@Inject
	private LoginCookieManager loginCookieManager;

	@Inject
	private UserStore userStore;

	public DemoPrincipal getValue(HttpContext context) {

		Principal principal = context.getRequest().getUserPrincipal();
		if (principal != null)
			return userStore.toDemoPrincipal(principal);

		HttpRequestContext request = context.getRequest();
		return loginCookieManager.getPrincipalFromCookies(request.getCookies());
	}

}