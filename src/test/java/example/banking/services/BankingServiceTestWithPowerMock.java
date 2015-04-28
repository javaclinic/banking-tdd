package example.banking.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import example.banking.dao.AccountDao;
import example.banking.dao.AccountNotFoundException;
import example.banking.domain.Account;

public class BankingServiceTestWithPowerMock {

	private static final double ERROR_TOLERANCE = 0.00_001;

	@Test
	public void testTransferWithPowerMock() throws AccountNotFoundException,
			InsufficientBalanceException {

		// Assemble - test setup
		AccountDao dao = ConfigurationService.getAccountDao();
		BankingService teller = ConfigurationService.getBankingService();

		// Test Fixture - setup test data
		double amount = 1000.0;
		Account fromAccount = dao.create("Jane Doe", 10_000.0);
		Account toAccount = dao.create("John Doe", 1_000.0);
		int fromAccountId = fromAccount.getId();
		int toAccountId = toAccount.getId();

		// Act - call business logic
		teller.transfer(fromAccountId, toAccountId, amount);

		// Verify - assert the results are what we expect
		Account finalFromAccount = dao.find(fromAccountId);
		Account finalToAccount = dao.find(toAccountId);
		assertEquals(9_000.0, finalFromAccount.getBalance(), ERROR_TOLERANCE);
		assertEquals(2_000.0, finalToAccount.getBalance(), ERROR_TOLERANCE);

		// Cleanup - test cleanup.

	}

}
