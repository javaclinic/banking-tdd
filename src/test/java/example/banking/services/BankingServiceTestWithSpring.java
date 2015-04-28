package example.banking.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import example.banking.dao.AccountDao;
import example.banking.dao.AccountNotFoundException;
import example.banking.domain.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:BankingSpringConfiguration.xml")
public class BankingServiceTestWithSpring {

	private static final double ERROR_TOLERANCE = 0.00_001;

	@Autowired
	private AccountDao dao;
	
	@Resource(name="bankingService")
	private BankingService teller;
	
	@Test
	public void testHelloWorld() throws Exception {
		assertEquals(1, 1);
	}

	@Test
	public void testTransfer() throws AccountNotFoundException,
			InsufficientBalanceException {

		// Assemble - test setup

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

	@Test
	public void testAccountIdIsNullIfNotInDatabase() {
		Account account = new Account();
		assertNull(account.getId());
	}

	@Test
	public void testAccountNotFoundInGet() {
		int accountId = 1;
		try {
			dao.find(accountId);
			fail("Did not catch AccountNotFoundException.");
		} catch (AccountNotFoundException e) {
			assertNotNull(e);
			assertEquals(accountId, e.getId().intValue());
			assertEquals("Account " + accountId + " was not found.",
					e.getMessage());
		}
	}

	@Test
	public void testTransferMoneyFromNonExistingAccount()
			throws InsufficientBalanceException {

		double amount = 1000.0;
		Account toAccount = dao.create("Jane Doe", 1_000.0);
		int toAccountId = toAccount.getId();
		int nonExistingAccountId = 3;

		try {
			assertNotNull(dao.find(toAccountId));
			teller.transfer(nonExistingAccountId, toAccountId, amount);
			fail("Did not throw AccountNotFoundException.");
		} catch (AccountNotFoundException e) {
			assertNotNull(e);
			assertEquals(nonExistingAccountId, e.getId().intValue());
			assertEquals("Account " + nonExistingAccountId + " was not found.",
					e.getMessage());
		}

	}

	@Test
	public void testTransferMoneyToNonExistingAccount()
			throws InsufficientBalanceException {

		double amount = 1000.0;
		Account fromAccount = dao.create("Jane Doe", 1_000.0);
		int fromAccountId = fromAccount.getId();
		int nonExistingAccountId = 3;

		try {
			assertNotNull(dao.find(fromAccountId));
			teller.transfer(fromAccountId, nonExistingAccountId, amount);
			fail("Did not throw AccountNotFoundException.");
		} catch (AccountNotFoundException e) {
			assertNotNull(e);
			assertEquals(nonExistingAccountId, e.getId().intValue());
			assertEquals("Account " + nonExistingAccountId + " was not found.",
					e.getMessage());
		}

	}

	@Test
	public void testInsufficientFunds() throws AccountNotFoundException {

		double amount = 10_000.0;
		Account fromAccount = dao.create("John Doe", 1_000.0);
		int fromAccountId = fromAccount.getId();
		Account toAccount = dao.create("Jane Doe", 1_000.0);
		int toAccountId = toAccount.getId();

		try {
			teller.transfer(fromAccountId, toAccountId, amount);
			fail("Did not throw InsufficientBalanceException.");
		} catch (InsufficientBalanceException e) {
			assertNotNull(e);
			assertEquals(fromAccountId, e.getAccountId().intValue());
			String expectedMessage = String.format(
					"Unable to withdraw $%.2f from %s", amount, fromAccount);
			assertEquals(expectedMessage, e.getMessage());
		}

	}

	@Test
	public void testTransferWithNegativeAmount()
			throws AccountNotFoundException, InsufficientBalanceException {

		double amount = -10_000.0;
		Account fromAccount = dao.create("John Doe", 1_000.0);
		int fromAccountId = fromAccount.getId();
		Account toAccount = dao.create("Jane Doe", 1_000.0);
		int toAccountId = toAccount.getId();

		try {
			teller.transfer(fromAccountId, toAccountId, amount);
			fail("Did not throw IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
			String expectedMessage = "Amount must be > 0, currently is "
					+ amount;
			assertEquals(expectedMessage, e.getMessage());
		}

	}

}
