package example.banking.dao;

import example.banking.domain.Account;

public interface AccountDao {
	Account create(int i, double d);
	Account find(int id);
}
