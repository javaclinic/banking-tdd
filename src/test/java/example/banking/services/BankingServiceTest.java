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
		teller.transfer(1,2, 1000.0);
	}
}
