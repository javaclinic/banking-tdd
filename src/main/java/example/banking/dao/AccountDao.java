package example.banking.dao;

import example.banking.domain.Account;

/**
 * AccountDao interface is a DAO interface for managing Account entities.
 * 
 */
public interface AccountDao {

	/**
	 * Creates a new account in the data layer. The data layer will assign a new
	 * id.
	 */
	Account create(String owner, double balance);

	/**
	 * Retrieves the account from the data layer with the given primary key.
	 * 
	 * @param id
	 * @return
	 * @throws AccountNotFoundException
	 */
	Account find(int id) throws AccountNotFoundException;

	/**
	 * Updates the account (or saves new one if one doesn't exist).
	 */
	void save(Account account);

}
