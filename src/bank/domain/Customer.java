package bank.domain;

import java.util.ArrayList;

public class Customer {

    private ArrayList<Account> accounts;
    private String firstName;
    private String lastName;
    private int customerNumber;

    private static int customerNumberBase = 1000;
    private int numOfAccounts;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getNumOfAccounts() {
        return numOfAccounts;
    }

    public Customer(String firstName, String lastName) {
        accounts = new ArrayList<>();
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerNumber = customerNumberBase++;
        numOfAccounts = 0;
    }

    public Account getAccount(int accountNo) {
        if (accountNo < accounts.size() && numOfAccounts != 0) {
            return accounts.get(accountNo);
        }
        return null;
    }

    @Override
    public String toString() {
        String s = "Customer: " + "fullName=" + firstName + ", " + lastName + ", customerNumber=" + customerNumber +
                ", numOfAccounts=" + numOfAccounts;
        for (int i = 0; i < this.numOfAccounts; i++) {
            Account account = getAccount(i);
            if (account instanceof SavingsAccount) {
                s = s + "\n\t¹" + (i + 1) + " Savings account with interest rate %" + ((SavingsAccount) account).getInterestRate();
            } else {
                s = s + "\n\t¹" + (i + 1) + " Checking account with overdraft $" + ((CheckingAccount) account).getOverdraftAmount();
            }
            s = s + ", balance $" + account.getBalance();
        }
        s = s + "\n";
        return s;
    }

    public void addAccount(Account account) {
        accounts.add(account);
        numOfAccounts++;
    }


}
