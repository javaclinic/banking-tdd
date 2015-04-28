package example.banking.services;

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
	 */
	void transfer(int fromAccountId, int toAccountId, double amount);

}
