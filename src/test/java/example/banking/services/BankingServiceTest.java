package example.banking.services;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import example.banking.dao.AccountDao;
import example.banking.dao.AccountNotFoundException;
import example.banking.dao.InMemoryAccountDao;
import example.banking.domain.Account;

public class BankingServiceTest {

	private static final double ERROR_TOLERANCE = 0.00_001;

	@Test
	public void testHelloWorld() throws Exception {
		Assert.assertEquals(1, 1);
	}

	@Test
	public void testTransfer() throws AccountNotFoundException,
			InsufficientBalanceException {

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
		try {
			dao.find(accountId);
			Assert.fail("Did not catch AccountNotFoundException.");
		} catch (AccountNotFoundException e) {
			Assert.assertNotNull(e);
			Assert.assertEquals(accountId, e.getId().intValue());
			Assert.assertEquals("Account " + accountId + " was not found.",
					e.getMessage());
		}
	}

	@Test
	public void testTransferMoneyFromNonExistingAccount()
			throws InsufficientBalanceException {

		AccountDao dao = new InMemoryAccountDao();
		BankingService teller = new SimpleBankingService(dao);

		double amount = 1000.0;
		Account toAccount = dao.create("Jane Doe", 1_000.0);
		int toAccountId = toAccount.getId();
		int nonExistingAccountId = 3;

		try {
			Assert.assertNotNull(dao.find(toAccountId));
			teller.transfer(nonExistingAccountId, toAccountId, amount);
			Assert.fail("Did not throw AccountNotFoundException.");
		} catch (AccountNotFoundException e) {
			Assert.assertNotNull(e);
			Assert.assertEquals(nonExistingAccountId, e.getId().intValue());
			Assert.assertEquals("Account " + nonExistingAccountId
					+ " was not found.", e.getMessage());
		}

	}

	@Test
	public void testTransferMoneyToNonExistingAccount()
			throws InsufficientBalanceException {

		AccountDao dao = new InMemoryAccountDao();
		BankingService teller = new SimpleBankingService(dao);

		double amount = 1000.0;
		Account fromAccount = dao.create("Jane Doe", 1_000.0);
		int fromAccountId = fromAccount.getId();
		int nonExistingAccountId = 3;

		try {
			Assert.assertNotNull(dao.find(fromAccountId));
			teller.transfer(fromAccountId, nonExistingAccountId, amount);
			Assert.fail("Did not throw AccountNotFoundException.");
		} catch (AccountNotFoundException e) {
			Assert.assertNotNull(e);
			Assert.assertEquals(nonExistingAccountId, e.getId().intValue());
			Assert.assertEquals("Account " + nonExistingAccountId
					+ " was not found.", e.getMessage());
		}

	}

	@Test
	public void testInsufficientFunds() throws AccountNotFoundException {

		AccountDao dao = new InMemoryAccountDao();
		BankingService teller = new SimpleBankingService(dao);

		double amount = 10_000.0;
		Account fromAccount = dao.create("John Doe", 1_000.0);
		int fromAccountId = fromAccount.getId();
		Account toAccount = dao.create("Jane Doe", 1_000.0);
		int toAccountId = toAccount.getId();

		try {
			teller.transfer(fromAccountId, toAccountId, amount);
			Assert.fail("Did not throw InsufficientBalanceException.");
		} catch (InsufficientBalanceException e) {
			Assert.assertNotNull(e);
			Assert.assertEquals(fromAccountId, e.getAccountId().intValue());
			String expectedMessage = String.format(
					"Unable to withdraw $%.2f from %s", amount, fromAccount);
			Assert.assertEquals(expectedMessage, e.getMessage());
		}

	}

	@Test
	public void testTransferWithNegativeAmount()
			throws AccountNotFoundException, InsufficientBalanceException {

		AccountDao dao = new InMemoryAccountDao();
		BankingService teller = new SimpleBankingService(dao);

		double amount = -10_000.0;
		Account fromAccount = dao.create("John Doe", 1_000.0);
		int fromAccountId = fromAccount.getId();
		Account toAccount = dao.create("Jane Doe", 1_000.0);
		int toAccountId = toAccount.getId();

		try {
			teller.transfer(fromAccountId, toAccountId, amount);
			Assert.fail("Did not throw IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			Assert.assertNotNull(e);
			String expectedMessage = "Amount must be > 0, currently is "
					+ amount;
			Assert.assertEquals(expectedMessage, e.getMessage());
		}

	}

	@Test
	public void testTransferWithMockito() throws AccountNotFoundException,
			InsufficientBalanceException {

		double amount = 1000.0;
		int fromAccountId = 1;
		int toAccountId = 2;
		String fromName = "Jane Doe";
		String toName = "John Doe";
		double fromBalance = 10_000.0;
		double toBalance = 1_000.0;

		Account fromAccount = new Account(fromAccountId, fromName, fromBalance);
		Account toAccount = new Account(toAccountId, toName, toBalance);

		// create mock object, mocking AccountDao interface
		AccountDao dao = Mockito.mock(AccountDao.class);

		// script mock object
		Mockito.when(dao.find(fromAccountId)).thenReturn(fromAccount);
		Mockito.when(dao.find(toAccountId)).thenReturn(toAccount);

		Assert.assertEquals(fromBalance, fromAccount.getBalance(), 0.00_001);
		Assert.assertEquals(toBalance, toAccount.getBalance(), 0.00_001);

		Assert.assertEquals(fromAccountId, fromAccount.getId().intValue());
		Assert.assertEquals(toAccountId, toAccount.getId().intValue());

		Assert.assertEquals(fromName, fromAccount.getOwner());
		Assert.assertEquals(toName, toAccount.getOwner());

		BankingService teller = new SimpleBankingService(dao);

		teller.transfer(fromAccountId, toAccountId, amount);

		// Mockito.verify(dao).save(fromAccount);
		// Mockito.verify(dao).save(toAccount);
		Mockito.verify(dao, Mockito.times(2)).save(Mockito.any(Account.class));
		Mockito.verify(dao, Mockito.never()).create(Mockito.anyString(), Mockito.anyDouble());

		Account finalFromAccount = dao.find(fromAccountId);
		Account finalToAccount = dao.find(toAccountId);
		Assert.assertEquals(9_000.0, finalFromAccount.getBalance(),
				ERROR_TOLERANCE);
		Assert.assertEquals(2_000.0, finalToAccount.getBalance(),
				ERROR_TOLERANCE);

	}

}
