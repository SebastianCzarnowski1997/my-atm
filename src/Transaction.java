import java.util.Date;

public class Transaction {
	private Date date;
	private double amount;
	
	Transaction(double amount) {
		this.amount = amount;
		this.date = new Date();
	}

	public Date getDate() {
		return date;
	}

	public double getAmount() {
		return amount;
	}

}
