package example.banking.dao;

import java.util.HashMap;
import java.util.Map;

import example.banking.domain.Account;

public class InMemoryAccountDao implements AccountDao {
	
	private Map<Integer,Account> database = new HashMap<>();
	
	@Override
	public Account create(int id, double balance) {
		Account newAccount = new Account(id,balance);
		database.put(id, newAccount);
		return newAccount;
	}

	@Override
	public Account find(int id) {
		return database.get(id);
	}
}
