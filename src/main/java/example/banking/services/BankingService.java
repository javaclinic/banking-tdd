package example.banking.services;

import example.banking.dao.AccountNotFoundException;

/**
 * BankingService API for dealing with accounts.
 */
public interface BankingService {

	/**
	 * Transfers amount of funds from one account to another.
	 * 
	 * @param fromAccountId
	 * @param toAccountId
	 * @param amount
	 * @throws AccountNotFoundException
	 *             if any of the source or target accounts does not exist
	 * @throws InsufficientBalanceException
	 *             if source account does not have sufficient funds
	 */
	void transfer(int fromAccountId, int toAccountId, double amount)
			throws AccountNotFoundException, InsufficientBalanceException;

}
