package example.banking.domain;

/**
 * Account entity.
 */
public class Account {

	private Integer id;
	private String owner;
	private double balance;

	/**
	 * Default constructor.
	 */
	public Account() {
	}

	/**
	 * Creates new account.
	 * 
	 * @param id
	 *            database primary key, or null if the Account is not yet in the
	 *            database
	 * @param owner
	 *            account owner name
	 * @param balance
	 *            current account balance
	 */
	public Account(Integer id, String owner, double balance) {
		this.id = id;
		this.owner = owner;
		this.balance = balance;
	}

	/**
	 * Returns database primary key, or null if the Account is not in the
	 * database.
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (Double.doubleToLongBits(balance) != Double
				.doubleToLongBits(other.balance))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Account [id=%s, owner=%s, balance=%s]", id,
				owner, balance);
	}

}
