package example.banking.services;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

import example.banking.dao.AccountDao;
import example.banking.dao.InMemoryAccountDao;
import example.banking.domain.Account;

public class BankingServiceTest {

	private static final double ERROR_TOLERANCE = 0.00_001;

	@Test
	public void testHelloWorld() throws Exception {
		Assert.assertEquals(1, 1);
	}

	@Test
	public void testTransfer() throws AccountNotFoundException {

		// Assemble - test setup
		AccountDao dao = new InMemoryAccountDao();
		BankingService teller = new SimpleBankingService(dao);

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
		Assert.assertEquals(9_000.0, finalFromAccount.getBalance(),
				ERROR_TOLERANCE);
		Assert.assertEquals(2_000.0, finalToAccount.getBalance(),
				ERROR_TOLERANCE);

		// Cleanup - test cleanup.

	}

	@Test
	public void testAccountIdIsNullIfNotInDatabase() {
		Account account = new Account();
		Assert.assertNull(account.getId());
	}

	@Test
	public void testAccountNotFoundInGet() {
		AccountDao dao = new InMemoryAccountDao();
		int accountId = 1;
		Account account = dao.find(accountId);
		Assert.assertNull(account);
	}

	@Test
	public void testTransferMoneyFromNonExistingAccount() {

		AccountDao dao = new InMemoryAccountDao();
		BankingService teller = new SimpleBankingService(dao);

		double amount = 1000.0;
		Account toAccount = dao.create("Jane Doe", 1_000.0);
		int toAccountId = toAccount.getId();
		int nonExistingAccountId = 3;

		try {
			Assert.assertNull(dao.find(nonExistingAccountId));
			Assert.assertNotNull(dao.find(toAccountId));
			teller.transfer(nonExistingAccountId, toAccountId, amount);
			Assert.fail("Did not throw AccountNotFoundException.");
		} catch (AccountNotFoundException e) {
			Assert.assertNotNull(e);
		}

	}

	@Test
	public void testTransferMoneyToNonExistingAccount() {

		AccountDao dao = new InMemoryAccountDao();
		BankingService teller = new SimpleBankingService(dao);

		double amount = 1000.0;
		Account fromAccount = dao.create("Jane Doe", 1_000.0);
		int fromAccountId = fromAccount.getId();
		int nonExistingAccountId = 3;

		try {
			Assert.assertNull(dao.find(nonExistingAccountId));
			Assert.assertNotNull(dao.find(fromAccountId));
			teller.transfer(fromAccountId, nonExistingAccountId, amount);
			Assert.fail("Did not throw AccountNotFoundException.");
		} catch (AccountNotFoundException e) {
			Assert.assertNotNull(e);
		}

	}

	@Test
	public void testInsufficientFunds() throws Exception {
		Assume.assumeNoException(new UnsupportedOperationException(
				"Not yet implemented"));
	}

}
