package example.banking.services;

import org.junit.Assert;
import org.junit.Test;

import example.banking.dao.AccountDao;
import example.banking.dao.InMemoryAccountDao;
import example.banking.domain.Account;

public class BankingServiceTest {
	
	private static final double ERROR_TOLERANCE = 0.00_001;
	
	@Test
	public void testHelloWorld() throws Exception {
		Assert.assertEquals(1,1);
	}
	
	@Test
	public void testTransfer() throws Exception {
		
		// Assemble - test setup
		AccountDao dao = new InMemoryAccountDao();
		BankingService teller = new SimpleBankingService();
		
		// Test Fixture - setup test data
		double amount = 1000.0;
		Account fromAccount = dao.create(1,10_000.0);
		Account toAccount = dao.create(2,1_000.0);
		int fromAccountId = fromAccount.getId();
		int toAccountId = toAccount.getId();
		
		// Act - call business logic
		teller.transfer(fromAccountId,toAccountId, amount);
		
		// Verify - assert the results are what we expect
		Account finalFromAccount = dao.find(fromAccountId);
		Account finalToAccount = dao.find(toAccountId);
		Assert.assertEquals(9_000.0, finalFromAccount.getBalance(), ERROR_TOLERANCE);
		Assert.assertEquals(2_000.0, finalToAccount.getBalance(), ERROR_TOLERANCE);
		
		// Cleanup - test cleanup.
		
	}
}
