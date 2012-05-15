package org.example.demo.service.cluster;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.infinispan.manager.DefaultCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class InfinispanService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private DefaultCacheManager cacheManager;

	@Inject
	public InfinispanService() throws IOException {
		logger.info("Inizializzazione " + this.getClass().getName());
		goInfinispan();
	}

	protected void goInfinispan() throws IOException {
		InputStream confInfinispan = this.getClass().getClassLoader().getResourceAsStream("infinispan.xml");
		cacheManager = new DefaultCacheManager(confInfinispan);
	}

	public void shutdown() {

		if (cacheManager == null)
			return;

		try {
			cacheManager.stop();
		} catch (Exception e) {
			logger.error("Error on Infinispan shutdown...", e);
		}

	}

	public DefaultCacheManager getCacheManager() {
		return cacheManager;
	}

	public void checkConnection() {

		if (cacheManager == null)
			return;

		Set<String> cacheNames = cacheManager.getCacheNames();
		for (String name : cacheNames) {
			if (!cacheManager.isRunning(name)) {
				throw new RuntimeException(name + " NamedCache is not running!");
			}
		}

	}

}
