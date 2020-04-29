package bank.domain;

import java.util.ArrayList;

public class Bank {

    private ArrayList<Customer> customers = new ArrayList<>();

    private int numOfClients = 0;

    private static Bank myBank = new Bank();

    private Bank() {
    }

    public static Bank getBank() {
        return myBank;
    }

    public Customer getCustomer(int customerNo) {
        if (customerNo < customers.size()) {
            return customers.get(customerNo);
        }
        return null;
    }

    public void addCustomer(Customer newCustomer) {
        customers.add(newCustomer);
        numOfClients++;

    }

    public int getNumOfClients() {
        return numOfClients;
    }


}
