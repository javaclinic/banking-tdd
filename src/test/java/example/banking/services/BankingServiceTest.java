package example.banking.services;

import org.junit.Assert;
import org.junit.Test;

public class BankingServiceTest {
	@Test
	public void testHelloWorld() throws Exception {
		Assert.assertEquals(1,1);
	}
	
	@Test
	public void testTransfer() throws Exception {
		
		// Assemble - test setup
		BankingService teller = new SimpleBankingService();
		
		// Test Fixture - setup test data
		int fromAccountId = 1;
		int toAccountId = 2;
		double amount = 1000.0;
		
		// Act - call business logic
		teller.transfer(fromAccountId,toAccountId, amount);
		
		// Verify - assert the results are what we expect
		
		// Cleanup - test cleanup.
		
	}
}
