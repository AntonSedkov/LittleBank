package bank.domain;

/**
 * Bank Account class
 *
 * @author Antonius
 */


public class Account {

    protected double balance;

    /**
     * Constructor that provides initial balance
     *
     * @param balance a positive account balance
     */
    protected Account(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            this.balance = 0;
        }
    }

    public Account() {
        this.balance = 0;
    }

    /**
     * Method to add money to account
     *
     * @param amount a positive amount of money
     */
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    /**
     * Method to withdraw money
     *
     * @param amount a positive amount of money less than balance
     */
    public boolean withdraw(double amount) throws Exception {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    /**
     * Method to check the account balance
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

}
