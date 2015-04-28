package example.banking.services;

import example.banking.dao.AccountDao;
import example.banking.dao.AccountNotFoundException;
import example.banking.domain.Account;

/**
 * Simple implementation of BankingService.
 */
public class SimpleBankingService implements BankingService {

	private AccountDao dao;

	public SimpleBankingService(AccountDao dao) {
		this.dao = dao;
	}

	@Override
	public void transfer(int fromAccountId, int toAccountId, double amount)
			throws AccountNotFoundException, InsufficientBalanceException {

		if (amount < 0)
			throw new IllegalArgumentException(
					"Amount must be > 0, currently is " + amount);

		Account fromAccount = dao.find(fromAccountId);
		Account toAccount = dao.find(toAccountId);

		fromAccount.withdrawFunds(amount);
		toAccount.addFunds(amount);

		dao.save(fromAccount);
		dao.save(toAccount);

	}

}
