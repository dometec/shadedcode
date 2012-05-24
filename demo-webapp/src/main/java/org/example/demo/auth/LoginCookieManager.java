package org.example.demo.auth;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.example.demo.service.cluster.TokenStore;

/**
 * Manage the marshalling/unmarshalling of login-token cookie
 * 
 * @author dometec
 *
 */
@Singleton
public class LoginCookieManager {

	private static final String LOGGIN_COOKIE_NAME = "logintoken";

	@Inject
	private TokenStore tokenStore;

	public ResponseBuilder login(ResponseBuilder response, String token) {
		return response.cookie(new NewCookie(LOGGIN_COOKIE_NAME, token));
	}

	public ResponseBuilder logout(ResponseBuilder response) {
		return response.cookie(new NewCookie(LOGGIN_COOKIE_NAME, "-1"));
	}

	public DemoPrincipal getPrincipalFromCookies(Map<String, javax.ws.rs.core.Cookie> cookies) {
		String token = getTokenFromCookies(cookies);
		if (token != null)
			return tokenStore.get(token);
		return null;
	}

	public String getTokenFromCookies(Map<String, javax.ws.rs.core.Cookie> cookies) {
		Set<String> keySet = cookies.keySet();
		for (String name : keySet) {
			javax.ws.rs.core.Cookie cookie = cookies.get(name);
			if (cookie.getName().equals(LOGGIN_COOKIE_NAME))
				return cookie.getValue();
		}
		return null;
	}

}
