package model.BO;

import java.util.ArrayList;

import model.DAO.AccountDAO;

public class AccountBO {
	public ArrayList<String> getAllUsernames() {
		return (new AccountDAO()).getAllUsernames();
	}
	
	public void createAccount(String username, String password, String role, String firstName, String lastName) {
		(new AccountDAO()).createAccount(username, password, role, firstName, lastName);
	}
}
