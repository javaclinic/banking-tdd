package example.banking.services;

import example.banking.dao.AccountDao;
import example.banking.dao.InMemoryAccountDao;

/**
 * Configuration service wires up important components.
 */
public class ConfigurationService {

	private static BankingService bankingService = new SimpleBankingService();
	private static AccountDao accountDao = new InMemoryAccountDao();

	public static BankingService getBankingService() {
		return bankingService;
	}

	public static AccountDao getAccountDao() {
		return accountDao;
	}

}
