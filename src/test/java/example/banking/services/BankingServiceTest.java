package example.banking.services;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import example.banking.dao.AccountDao;
import example.banking.domain.Account;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BankingServiceTest {
	
	private static final double ERROR_TOLERANCE = 0.00_001;
	
	@Test
	public void testHelloWorld() throws Exception {
		Assert.assertEquals(1,1);
	}
	
	@Test
	public void test_02_Transfer() throws Exception {
		
		// Assemble - test setup
		BankingService teller = ConfigurationService.getBankingService();
		AccountDao dao = ConfigurationService.getAccountDao();
		
		// Test Fixture - setup test data
		double amount = 1000.0;
		Account fromAccount = dao.create("Jane Doe",10_000.0);
		Account toAccount = dao.create("John Doe",1_000.0);
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
	
	@Test
	public void testAccountIdIsNullIfNotInDatabase() throws Exception {
		Assume.assumeNoException(new UnsupportedOperationException("Not yet implemented"));
	}
	
	@Test
	public void test_01_AccountNotFoundInGet() throws Exception {
		// Assemble - test setup
		AccountDao dao = ConfigurationService.getAccountDao();
		
		// Test Fixture - setup test data
		int accountId = 1;
		
		// Act - call business logic
		Account account = dao.find(accountId);
		
		// Verify - assert the results are what we expect
		Assert.assertNull(account);
		
		// Cleanup - test cleanup
		
	}
	
	@Test
	public void testInsufficientFunds() throws Exception {
		Assume.assumeNoException(new UnsupportedOperationException("Not yet implemented"));
	}
	
}
