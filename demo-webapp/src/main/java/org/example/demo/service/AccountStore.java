package org.example.demo.service;

import java.util.Hashtable;
import java.util.Map;

import javax.inject.Singleton;

@Singleton
public class AccountStore {

	private Map<String, Integer> accountdb;

	public AccountStore() {
		accountdb = new Hashtable<String, Integer>();
		accountdb.put("user1", 100);
		accountdb.put("user2", 100);
		accountdb.put("user3", 100);
	}

	public Map<String, Integer> getAll() {
		return accountdb;
	}

	public Integer getAmount(String user) {
		return accountdb.get(user);
	}

	public void decreaseAmount(String user, Integer amount) {
		Integer ua = accountdb.get(user);
		accountdb.put(user, ua - amount);
	}

	public void increaseAmount(String user, Integer amount) {
		Integer ua = accountdb.get(user);
		accountdb.put(user, ua + amount);
	}

}
