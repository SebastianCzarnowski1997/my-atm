import java.util.ArrayList;

public class Bank {

	private String name; // name of the bank

	private ArrayList<User> users; // users that are assign in the bank

	Bank(String name) {
		this.name = name;
		this.users = new ArrayList<User>();
	}

	/*
	 * This function adding the user to the bank
	 */
	public void addUserToTheBank(User user) {
		this.users.add(user);
	}

	public User checkIfUserHaveAccess(String login, int pin) {
		for (User user : this.users) {
			boolean ifAccess = user.checkIfAccess(login, pin);
			if (ifAccess) {
				return user;
			}
		}
		return null;
	}
}
