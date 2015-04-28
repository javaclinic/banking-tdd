package example.banking.services;

import example.banking.domain.Account;

public class InsufficientBalanceException extends Exception {

	private static final long serialVersionUID = 7690894121562313713L;

	private final Integer accountId;
	private final double accountBalance;
	private final double withdrawAmount;

	public InsufficientBalanceException(Account account, double withdrawAmount) {
		super(String.format("Unable to withdraw $%.2f from %s", withdrawAmount,
				account));
		this.accountId = account.getId();
		this.accountBalance = account.getBalance();
		this.withdrawAmount = withdrawAmount;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public double getWithdrawAmount() {
		return withdrawAmount;
	}

}
