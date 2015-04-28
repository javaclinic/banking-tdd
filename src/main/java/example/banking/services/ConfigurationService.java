package example.banking.services;

import example.banking.dao.AccountDao;
import example.banking.dao.InMemoryAccountDao;

/**
 * Configuration service wires up important components.
 */
public class ConfigurationService {

	private static BankingService bankingService;
	private static AccountDao accountDao;

	static {
		reset();
	}
	
	public static BankingService getBankingService() {
		return bankingService;
	}

	public static AccountDao getAccountDao() {
		return accountDao;
	}
	
	public static synchronized void reset() {
		accountDao = new InMemoryAccountDao();
		bankingService = new SimpleBankingService();
	}

}
