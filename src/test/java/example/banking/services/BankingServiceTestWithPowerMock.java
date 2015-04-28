package example.banking.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import example.banking.dao.AccountDao;
import example.banking.dao.AccountNotFoundException;
import example.banking.dao.InMemoryAccountDao;
import example.banking.domain.Account;

@RunWith(PowerMockRunner.class)
public class BankingServiceTestWithPowerMock {

	private static final double ERROR_TOLERANCE = 0.00_001;

	@Test
	public void testHelloWorld() throws Exception {
		assertEquals(1, 1);
	}

	@PrepareForTest(ConfigurationService.class)
	@Test
	public void testTransferWithPowerMock() throws AccountNotFoundException,
			InsufficientBalanceException {

		// Assemble - test setup
		AccountDao dummyDao = new InMemoryAccountDao();
		BankingService dummyTeller = new SimpleBankingService(dummyDao);

		// Test Fixture - setup test data
		PowerMockito.mockStatic(ConfigurationService.class);
		when(ConfigurationService.getAccountDao()).thenReturn(dummyDao);
		when(ConfigurationService.getBankingService()).thenReturn(dummyTeller);

		AccountDao dao = ConfigurationService.getAccountDao();

		double amount = 1000.0;
		Account fromAccount = dao.create("Jane Doe", 10_000.0);
		Account toAccount = dao.create("John Doe", 1_000.0);
		int fromAccountId = fromAccount.getId();
		int toAccountId = toAccount.getId();

		// Act - call business logic
		dummyTeller.transfer(fromAccountId, toAccountId, amount);

		// Verify - assert the results are what we expect
		Account finalFromAccount = dao.find(fromAccountId);
		Account finalToAccount = dao.find(toAccountId);
		assertEquals(9_000.0, finalFromAccount.getBalance(), ERROR_TOLERANCE);
		assertEquals(2_000.0, finalToAccount.getBalance(), ERROR_TOLERANCE);

		// Cleanup - test cleanup.

	}

}
