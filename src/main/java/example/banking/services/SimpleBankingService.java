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

		Account fromAccount = dao.find(fromAccountId);
		Account toAccount = dao.find(toAccountId);

		double fromBalance = fromAccount.getBalance();
		if (fromBalance < amount)
			throw new InsufficientBalanceException(fromAccount, amount);
		fromBalance -= amount;
		fromAccount.setBalance(fromBalance);

		double toBalance = toAccount.getBalance();
		toBalance += amount;
		toAccount.setBalance(toBalance);

		dao.save(fromAccount);
		dao.save(toAccount);

	}

}
