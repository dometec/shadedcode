package org.example.demo.service.cluster;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.example.demo.auth.DemoPrincipal;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TokenStore extends GenericClusterService<String, DemoPrincipal> {

	@Inject
	public TokenStore(InfinispanService infinispanService) {
		super(infinispanService);
	}

	protected String getCacheName() {
		return "auth-token-cache";
	}

	public String createToken(DemoPrincipal principal) {
		String uuid = UUID.randomUUID().toString();
		put(uuid, principal);
		return uuid;
	}

	public String getExpireByToken(String token) {
		DateFormat sdf = SimpleDateFormat.getTimeInstance(SimpleDateFormat.FULL);
		return sdf.format(getExpirationDate(token));
	}

}
