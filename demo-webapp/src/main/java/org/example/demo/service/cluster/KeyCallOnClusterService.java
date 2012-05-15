package org.example.demo.service.cluster;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class KeyCallOnClusterService extends GenericClusterService<String, String> {

	@Inject
	public KeyCallOnClusterService(InfinispanService infinispanService) {
		super(infinispanService);
	}

	protected String getCacheName() {
		return "current-call-cache";
	}

}
