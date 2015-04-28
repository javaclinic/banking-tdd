package example.banking.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import example.banking.domain.Account;

/**
 * InMemoryAccountDao uses in-memory Map to store accounts.
 * 
 * This implementation is a Singleton.
 * 
 */
public class InMemoryAccountDao implements AccountDao {

	private Map<Integer, Account> database = new HashMap<>();
	private static AtomicInteger counter = new AtomicInteger(0);

	@Override
	public Account create(String owner, double balance) {
		Integer id = counter.incrementAndGet();
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
