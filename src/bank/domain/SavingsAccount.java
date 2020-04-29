package bank.domain;

public class SavingsAccount extends Account {

    private double interestRate;

    public SavingsAccount(double initBalance, double interestRate) {
        this.balance = initBalance;
        this.interestRate = interestRate;
    }

    public void addInterestRate() {
        this.balance *= (1 + this.interestRate/100);
    }

    public double getInterestRate() {
        return interestRate;
    }
}
