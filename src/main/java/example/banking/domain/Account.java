package example.banking.domain;

/**
 * Account entity.
 */
public class Account {
	
	private int id;
	private String owner;
	private double balance;
	
	public Account(int id, String owner, double balance) {
		this.id = id;
		this.owner = owner;
		this.balance = balance;
	}
	
	public int getId() {
		return id;
	}
	
	public double getBalance() {
		return balance;
	}
	
}
