package example.banking.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import example.banking.domain.Account;

/**
 * InMemoryAccountDao is a in-memory implementation of AccountDao. It uses
 * in-memory Map to store accounts.
 */
public class InMemoryAccountDao implements AccountDao {

	private Map<Integer, Account> database;
	private static int counter = 1;

	/**
	 * Default implementation of the internal database is synchronized HashMap.
	 */
	public InMemoryAccountDao() {
		this.database = Collections
				.synchronizedMap(new HashMap<Integer, Account>());
	}

	public InMemoryAccountDao(Map<Integer, Account> database) {
		this.database = database;
	}

	@Override
	public synchronized Account create(String owner, double balance) {
		Integer id = counter++;
		Account newAccount = new Account(id, owner, balance);
		database.put(id, newAccount);
		return newAccount;
	}

	@Override
	public Account find(int id) {
		return database.get(id);
	}

	@Override
	public void save(Account account) {
		Integer id = account.getId();
		database.put(id, account);
	}

}
