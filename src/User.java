import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
	private String name;
	private String lastName;
	private String login;
	private String hashPin;
	private ArrayList<Account> accounts;

	public User(String name, String lastName, String login, int pin) {
		this.name = name;
		this.lastName = lastName;
		this.login = login;
		this.accounts = new ArrayList<Account>();
		Account savingsAccount = new Account("savings", 100);
		this.accounts.add(savingsAccount);
		try {
			this.hashPin = this.hashPin(pin);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("You cannot add this PIN");
		}
	}

	public String hashPin(int pin) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(String.valueOf(pin).getBytes(StandardCharsets.UTF_8));
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public boolean checkIfAccess(String login, int pin) {
		try {
			if (this.hashPin(pin).equals(this.hashPin) && this.login.equals(login)) {
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Cannot hash the pin");
		}
		return false;
	}

	public String getName() {
		return this.name;
	}

	public void addAccount(Account acc1) {
		this.accounts.add(acc1);
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

}
