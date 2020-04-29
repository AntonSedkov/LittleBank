package bank.domain;

public class AccumulateSavingsBatch {

    public AccumulateSavingsBatch() {
    }

    public void doBatch() {

        Bank bank = Bank.getBank();

        //For each customer...
        for (int cust_idx = 0; cust_idx < bank.getNumOfClients(); cust_idx++) {
            Customer customer = bank.getCustomer(cust_idx);

            //For each account for this customer ...
            for (int acc_idx = 0; acc_idx < customer.getNumOfAccounts(); acc_idx++) {
                Account account = customer.getAccount(acc_idx);
                String account_type = "";

                //Determine the account type
                if (account instanceof SavingsAccount) {
                    SavingsAccount savings = (SavingsAccount) account;
                    savings.addInterestRate();
                } else {
                    //ignore all other account types
                }
            }
        }
    }
}
