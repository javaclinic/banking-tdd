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
		Account a1 = teller.findAccount(1);
		Account a2 = teller.findAccount(2);
		a1.setBalance(a1.getBalance() - 1000);
		a2.setBalance(a2.getBalance() + 1000);
		teller.saveAccount(a1);
		teller.saveAccount(a2);
	}
}
