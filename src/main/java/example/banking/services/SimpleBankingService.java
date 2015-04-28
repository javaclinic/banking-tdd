package example.banking.services;

import example.banking.dao.AccountDao;
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
			throws AccountNotFoundException {

		Account fromAccount = dao.find(fromAccountId);
		if ( fromAccount == null ) throw new AccountNotFoundException();
		
		Account toAccount = dao.find(toAccountId);
		if ( toAccount == null ) throw new AccountNotFoundException();
		
		double fromBalance = fromAccount.getBalance();
		fromBalance -= amount;
		fromAccount.setBalance(fromBalance);

		double toBalance = toAccount.getBalance();
		toBalance += amount;
		toAccount.setBalance(toBalance);

		dao.save(fromAccount);
		dao.save(toAccount);

	}

}
