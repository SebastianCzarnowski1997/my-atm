import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); // create scanner
		Bank bank = new Bank("Millenium"); // create new bank with name millenium
		User user1 = new User("Sebastian", "Czarnowski", "czs2s", 1234);
		User user2 = new User("Karol", "Marek", "kr123", 4321);
		Account account = new Account("Checking", 2000);
		bank.addUserToTheBank(user1); // adding to the bank user1
		bank.addUserToTheBank(user2); // adding user2
		user1.addAccount(account);
		user2.addAccount(account);
		tryUserToLogIn(bank, sc);
	}

	private static void promptATMOptionsToUser(Scanner sc, Account acc, User currentUser, Bank bank) {
		String[] optionsForUser = { "Show account transaction history", "Withdrawal", "Deposit", "Transfer",
				"Show account sum", "Back to account selection" };
		int choice = 0;
		do {
			System.out.printf("Welcome %s, what would you like to do: \n", currentUser.getName());
			for (int i = 1; i <= optionsForUser.length; i++) {
				System.out.println(i + ") " + optionsForUser[i - 1]);
			}
			try {
				System.out.println();
				System.out.println("Enter choice: ");
				choice = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a valid number value.");
				sc.next();
			}
			
		} while (choice > 6 || choice < 1);
		switch (choice) {
		case 1:
			showAccountTransactions(sc, acc, bank, currentUser);
			break;
		case 2:
			withdrawalMoneyFromAccount(sc, acc, bank, currentUser);
			break;
		case 3:
			depositMoneyToTheAccount(sc, acc, bank, currentUser);
			break;
		case 4:
			tranferMoneyToDifferentAccount(sc, acc, bank, currentUser);
			break;
		case 5:
			showAccountSum(sc, acc, bank, currentUser);
			break;
		case 6:
			loginAccount(bank, sc, currentUser);
			break;
		}
	}

	private static void tranferMoneyToDifferentAccount(Scanner sc, Account acc, Bank bank, User currentUser) {
		
	}

	private static void depositMoneyToTheAccount(Scanner sc, Account acc, Bank bank, User currentUser) {
		double depositAmount;
		do {
			System.out.print("Enter deposit amount: ");
			try {
				depositAmount = sc.nextDouble();
				acc.depositAmmount(depositAmount);
				System.out.println("Your deposit is successfull. Now your account have amount: " + acc.getAmount());
				redirectToThePromptMenu(sc, acc, bank, currentUser);
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a valid number value.");
				sc.next();
			}

		} while (true);
		System.out.println("Deposit amount: " + depositAmount);
	}

	private static void withdrawalMoneyFromAccount(Scanner sc, Account acc, Bank bank, User currentUser) {
		double withdrawalAmount;
		do {
			System.out.println("How much do you want to withdrawal from your account: ");
			try {
				withdrawalAmount = sc.nextDouble();
				if (acc.getAmount() > withdrawalAmount) {
					acc.withdrawl(withdrawalAmount);
					System.out.println(
							"Here is your: " + withdrawalAmount + ". In your account left: " + acc.getAmount());
					redirectToThePromptMenu(sc, acc, bank, currentUser);
				} else {
					System.out.println("You have on the account: " + acc.getAmount() + " but you want to withdrawl: "
							+ withdrawalAmount);
					withdrawalMoneyFromAccount(sc, acc, bank, currentUser);
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a valid number value.");
				sc.next();
			}
		} while (true);
	}

	private static void showAccountTransactions(Scanner sc, Account acc, Bank bank, User currentUser) {
		System.out.println(
				"Your number of transations is: " + acc.getAmountOfTransactions() + ": " + acc.getTransactions());
		redirectToThePromptMenu(sc, acc, bank, currentUser);
	}

	private static void showAccountSum(Scanner sc, Account acc, Bank bank, User currentUser) {
		System.out.println("Your total on " + acc.getName() + " account is " + acc.getAmount());
		redirectToThePromptMenu(sc, acc, bank, currentUser);
	}

	private static void tryUserToLogIn(Bank bank, Scanner sc) {
		int attempts = 3;
		User userWithAccess = null;
		while (attempts > 0) {
			System.out.println("Please enter login: ");
			String login = sc.nextLine();
			System.out.println("Please enter PIN: ");
			int pin = sc.nextInt();
			sc.nextLine();
			userWithAccess = bank.checkIfUserHaveAccess(login, pin);
			if (userWithAccess == null) {
				attempts--;
				System.out
						.println("Wrong login or PIN. Please try again. You have left with: " + attempts + " attempts");
			} else {
				System.out.println("Hi " + userWithAccess.getName());
				break;
			}
		}
		loginAccount(bank, sc, userWithAccess);
	}

	private static void loginAccount(Bank bank, Scanner sc, User currentUser) {
		ArrayList<Account> accounts = currentUser.getAccounts();
		if (accounts.isEmpty()) {
			System.out.println("You have no accounts to perform transactions on.");
		}

		System.out.println("Please select an account that you want to make transactions with: ");
		for (int i = 0; i < accounts.size(); i++) {
			System.out.println(i + ": " + currentUser.getAccounts().get(i).getName());
		}
		System.out.println(accounts.size() + ": " + "click for log out");

		int accountIndex = -1;
		while (accountIndex < 0 || accountIndex >= accounts.size() + 1) {
			System.out.print("Enter the account number: ");
			try {
				accountIndex = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a number.");
			}

			if (accountIndex < 0 || accountIndex >= accounts.size() + 1) {
				System.out.println("Invalid account number. Please try again.");
			}
		}
		if (accountIndex == accounts.size()) {
			tryUserToLogIn(bank, sc);
		}
		Account selectedAccount = accounts.get(accountIndex);
		System.out.println("Selected account: " + selectedAccount.getName());
		promptATMOptionsToUser(sc, selectedAccount, currentUser, bank);
	}

	private static void redirectToThePromptMenu(Scanner sc, Account acc, Bank bank, User currentUser) {
		String choice = sc.nextLine();
		if (!choice.equals("q")) {
			System.out.println("Select q to quit");
			redirectToThePromptMenu(sc, acc, bank, currentUser);
		} else
			promptATMOptionsToUser(sc, acc, currentUser, bank);
	}

}
