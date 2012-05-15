package org.example.demo.service.cluster;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.transaction.TransactionManager;

import org.infinispan.AdvancedCache;
import org.infinispan.Cache;
import org.infinispan.container.DataContainer;
import org.infinispan.container.entries.InternalCacheEntry;
import org.infinispan.manager.DefaultCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GenericClusterService<K extends Serializable, V extends Serializable>   {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	protected Map<K, V> cache;

	public GenericClusterService(InfinispanService infinispanService) {
		logger.info("Inizializzazione " + this.getClass().getName());

		cache = new Hashtable<K, V>();

		DefaultCacheManager cacheManager = infinispanService.getCacheManager();
		if (cacheManager != null)
			cache = cacheManager.getCache(getCacheName());

	}

	protected abstract String getCacheName();

	public TransactionManager getTransactionManager() {

		if (cache instanceof Hashtable)
			return null;

		AdvancedCache<K, V> advancedCache = ((Cache<K, V>) cache).getAdvancedCache();
		return advancedCache.getTransactionManager();
	}

	@SuppressWarnings("unchecked")
	public boolean lock(K key) {

		if (cache instanceof Hashtable)
			return true;

		AdvancedCache<K, V> advancedCache = ((Cache<K, V>) cache).getAdvancedCache();
		return advancedCache.lock(key);
	}

	public void put(K key, V value) {
		cache.put(key, value);
	}

	public V get(K key) {
		return cache.get(key);
	}

	public Set<Entry<K, V>> getEntrySet() {
		return cache.entrySet();
	}

	public void remove(Serializable key) {
		cache.remove(key);
	}

	public void clear() {
		cache.clear();
	}

	public Date getExpirationDate(K key) {

		if (cache instanceof Hashtable)
			return new Date(Long.MAX_VALUE);

		Cache<K, V> _cache = (Cache<K, V>) cache;
		DataContainer dataContainer = _cache.getAdvancedCache().getDataContainer();
		InternalCacheEntry internalCacheEntry = dataContainer.get(key);

		if (internalCacheEntry == null)
			return new Date();

		return new Date(internalCacheEntry.getExpiryTime());
	}

}
