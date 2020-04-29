package bank;

import bank.domain.*;

public class TestAccount {
    public static void main(String[] args) {

        Bank bank = Bank.getBank();

        Customer firstCustomer = new Customer("Decent", "Raccoon");
        Customer secondCustomer = new Customer("Civil", "Sprat");

        SavingsAccount raccoonSavings = new SavingsAccount(1000, 5);
        CheckingAccount raccoonAccount = new CheckingAccount(5000, 1000);
        CheckingAccount spratAccount = new CheckingAccount(500, 100);

        firstCustomer.addAccount(raccoonSavings);
        firstCustomer.addAccount(raccoonAccount);
        secondCustomer.addAccount(spratAccount);

        bank.addCustomer(firstCustomer);
        bank.addCustomer(secondCustomer);

        showCustomer(bank);

        bank.getCustomer(0).getAccount(0).deposit(1000);
        try {
            bank.getCustomer(0).getAccount(1).withdraw(5500);
        } catch (OverdraftException ex) {
            System.out.println(ex.getMessage() + ": $" + ex.getDeficit() + "!\n");
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        ((SavingsAccount) bank.getCustomer(0).getAccount(0)).addInterestRate();

        showCustomer(bank);


    }

    public static void showCustomer(Bank bank) {
        System.out.println(bank.getCustomer(0));
        System.out.println(bank.getCustomer(1));
    }
}
