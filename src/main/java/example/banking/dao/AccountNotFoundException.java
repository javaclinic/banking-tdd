package example.banking.dao;

public class AccountNotFoundException extends Exception {

	private static final long serialVersionUID = -3171798687585210775L;

	public AccountNotFoundException(String message) {
		super(message);
	}

}
