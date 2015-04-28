package example.banking.dao;

public class AccountNotFoundException extends Exception {

	private static final long serialVersionUID = -9168369830633706698L;

	private final Integer id;

	public AccountNotFoundException(Integer id) {
		super(String.format("Account %d was not found.", id));
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

}
