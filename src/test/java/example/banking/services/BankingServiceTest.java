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
		BankingService teller = new SimpleBankingService();
		int fromAccountId = 1;
		int toAccountId = 2;
		double amount = 1000.0;
		teller.transfer(fromAccountId,toAccountId, amount);
	}
}
