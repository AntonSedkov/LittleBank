package bank.domain;

public class CheckingAccount extends Account {

    private double overdraftAmount;

    public CheckingAccount(double initBalance, double overdraftAmount) {
        this.balance = initBalance;
        this.overdraftAmount = overdraftAmount;
    }

    public CheckingAccount(double initBalance) {
        this(initBalance, 0);
    }

    @Override
    public boolean withdraw(double amount) throws OverdraftException {
        if (amount <= this.balance + overdraftAmount && amount > 0) {
            balance = balance - amount;
            return true;
        }
        throw new OverdraftException(amount - balance - overdraftAmount, "Error! Insufficient funds");
    }

    public double getOverdraftAmount() {
        return overdraftAmount;
    }
}
