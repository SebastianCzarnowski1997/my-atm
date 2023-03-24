import java.util.ArrayList;

public class Account {
	private String name;
	
	private ArrayList<Transaction> transactions;
	
	private double amount;
	
	
	Account(String name, double amount) {
		this.name = name;
		this.amount = amount;
		transactions = new ArrayList<Transaction>();
	}


	public String getName() {
		return name;
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public String getTransactions() {
		String concatTransactions = "";
		for (Transaction transaction : this.transactions) {
			concatTransactions += "date of transaction: " +transaction.getDate() + ", amount: "+transaction.getAmount();
		}
		return concatTransactions;
	}
	
	public int getAmountOfTransactions() {
		return this.transactions.size();
	}
	
	public void withdrawl(double value) {
		this.amount -= value;
	}
	
	public void depositAmmount(double value) {
		this.amount += value;
	}

	
}
