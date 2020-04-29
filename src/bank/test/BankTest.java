package bank.test;

import bank.domain.Bank;
import bank.domain.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    Bank bank = Bank.getBank();

    Customer firstCustomer = new Customer("Decent", "Raccoon");

    @Test
    public void getBank() {
        System.out.println("getBank - only one instance of Bank");
        Bank expResult = bank;
        Bank result = Bank.getBank();
        assertEquals(expResult, result);
    }

    @Test
    public void getCustomer() {
        System.out.println("getCustomer");
        Bank instance = bank;
        bank.addCustomer(firstCustomer);
        Customer expResult = firstCustomer;
        int custNo = 0;
        Customer result = instance.getCustomer(custNo);
        assertEquals(expResult, result);
    }

    @Test
    public void addCustomer() {
        System.out.println("addCustomer");
        Customer newCustomer = new Customer("new", "customer");
        Bank instance = bank;
        instance.addCustomer(newCustomer);
        assertEquals(newCustomer, instance.getCustomer(bank.getNumOfClients() - 1));
    }

    @Test
    public void getNumOfClients() {
        System.out.println("getNumOfClients");
        Bank instance = bank;
        int expResult = 3;                                              //bank.addCustomer:     test#2+test#3* 2customers
        int result = instance.getNumOfClients() + 1;
        assertEquals(expResult, result);
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

}

