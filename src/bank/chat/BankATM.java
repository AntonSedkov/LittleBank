package bank.chat;

import bank.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BankATM extends JFrame {

    Bank bank = Bank.getBank();
    Customer currentCustomer;
    Account currentAccount;

    public BankATM() {

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

        setPreferredSize(new Dimension(800, 350));
        setSize(new Dimension(800, 350));
        setTitle("My Bank ATM");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    private JTextField amountField, statusField;
    private JButton balanceButton, depositButton, pointButton, enterButton, withdrawButton, chooseButton;
    private JButton oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton, zeroButton;

    private JTextArea historyArea;
    private JPanel jPanel1, jPanel2, jPanel3;
    private JScrollPane jScrollPane1;

    private void initComponents() {

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        balanceButton = new JButton("Check account balance");
        depositButton = new JButton("Make a deposit");
        withdrawButton = new JButton("Make a withdrawal");
        chooseButton = new JButton("Choose your account");
        amountField = new JTextField("");
        jPanel3 = new JPanel();
        oneButton = new JButton("1");
        twoButton = new JButton("2");
        threeButton = new JButton("3");
        fourButton = new JButton("4");
        fiveButton = new JButton("5");
        sixButton = new JButton("6");
        sevenButton = new JButton("7");
        eightButton = new JButton("8");
        nineButton = new JButton("9");
        zeroButton = new JButton("0");
        pointButton = new JButton(".");
        enterButton = new JButton("Enter");
        enterButton.addActionListener(actionEvent -> enterButtonActionPerfomed(actionEvent));


        jScrollPane1 = new JScrollPane();
        historyArea = new JTextArea(5, 20);
        statusField = new JTextField("Welcome to my Bank. Enter the client ID and press ENTER...");

        jPanel1.setLayout(new GridLayout(2, 1));

        jPanel2.setLayout(new GridLayout(5, 1));
        jPanel2.add(chooseButton);
        chooseButton.setEnabled(false);
        chooseButton.addActionListener(actionEvent -> chooseAction(actionEvent));
        jPanel2.add(balanceButton);
        balanceButton.setEnabled(false);
        balanceButton.addActionListener(actionEvent -> balanceAction(actionEvent));
        jPanel2.add(depositButton);
        depositButton.setEnabled(false);
        depositButton.addActionListener(actionEvent -> depositAction(actionEvent));
        jPanel2.add(withdrawButton);
        withdrawButton.setEnabled(false);
        withdrawButton.addActionListener(actionEvent -> withdrawAction(actionEvent));
        jPanel2.add(amountField);
        jPanel1.add(jPanel2);

        jPanel3.setLayout(new GridLayout(4, 3));
        jPanel3.add(oneButton);
        jPanel3.add(twoButton);
        jPanel3.add(threeButton);
        jPanel3.add(fourButton);
        jPanel3.add(fiveButton);
        jPanel3.add(sixButton);
        jPanel3.add(sevenButton);
        jPanel3.add(eightButton);
        jPanel3.add(nineButton);
        jPanel3.add(zeroButton);
        jPanel3.add(pointButton);
        jPanel3.add(enterButton);
        jPanel1.add(jPanel3);

        for (Component c : jPanel3.getComponents()) {
            if ((c.getClass().equals(JButton.class)) && (((JButton) c).getText() != "Enter")) {
                JButton currentJButton = (JButton) c;
                currentJButton.addActionListener(actionEvent -> buttonAction(actionEvent));
            }
        }

        getContentPane().add(jPanel1, BorderLayout.LINE_START);

        historyArea.setEditable(false);
        jScrollPane1.setViewportView(historyArea);
        getContentPane().add(jScrollPane1, BorderLayout.CENTER);

        statusField.setEditable(false);
        getContentPane().add(statusField, BorderLayout.PAGE_END);


        pack();
    }

    private void chooseAction(ActionEvent actionEvent) {
        try {
            int accountID = Integer.parseInt(amountField.getText());
            currentAccount = currentCustomer.getAccount(accountID);

            if (currentAccount != null) {
                historyArea.append("You choose the account ID " + accountID + ". This is ");
                if (currentAccount instanceof CheckingAccount) {
                    historyArea.append("a Checking Account with overdraft protection $"
                            + ((CheckingAccount) currentAccount).getOverdraftAmount() + "\n");
                } else if (currentAccount instanceof SavingsAccount) {
                    historyArea.append("a Savings Account with interest rate "
                            + ((SavingsAccount) currentAccount).getInterestRate() + "%\n");
                }
                balanceButton.setEnabled(true);
                depositButton.setEnabled(true);
                withdrawButton.setEnabled(true);
                statusField.setText(currentCustomer.getFirstName() + " " + currentCustomer.getLastName() + ". Your account ID " + accountID);
            } else {
                historyArea.append("Error: Account not found or incorrect Account ID!\n");
            }
        } catch (Exception ex) {
            historyArea.append("Error: Account not found or incorrect Account ID!\n");
        }
        amountField.setText("");
    }

    private void withdrawAction(ActionEvent actionEvent) {
        try {
            double amt = Double.parseDouble(amountField.getText());
            if (currentAccount.withdraw(amt)) {
                historyArea.append("Withdraw: $" + amt + ", new balance is $" + currentAccount.getBalance() + "\n");
            } else {
                historyArea.append("Error: Insufficient funds!\n");
            }
        } catch (Exception ex) {
            historyArea.append("Error: can't complete withdrawal operation!\n");
        }
        amountField.setText("");
    }

    private void depositAction(ActionEvent actionEvent) {
        try {
            double amt = Double.parseDouble(amountField.getText());
            currentAccount.deposit(amt);
            historyArea.append("Deposit: $" + amt + ", new balance is $" + currentAccount.getBalance() + "\n");
        } catch (Exception ex) {
            historyArea.append("Error: can't complete deposit operation!\n");
        }
        amountField.setText("");
    }

    private void enterButtonActionPerfomed(ActionEvent event) {
        try {
            int customerID = Integer.parseInt(amountField.getText());
            currentCustomer = bank.getCustomer(customerID);
            historyArea.append("Customer with ID = " + customerID + " is " + currentCustomer.getLastName() + ", " + currentCustomer.getFirstName() + "\n");
            chooseButton.setEnabled(true);
            enterButton.setEnabled(false);
            statusField.setText(currentCustomer.getFirstName() + " " + currentCustomer.getLastName() + " Please, choose your account");
        } catch (Exception ex) {
            historyArea.append("Error: Customer not found or incorrect Customer ID!\n");
        }
        amountField.setText("");
    }

    private void buttonAction(ActionEvent event) {
        amountField.setText(amountField.getText() + ((JButton) event.getSource()).getText());
    }

    private void balanceAction(ActionEvent actionEvent) {
        historyArea.append("Balance of " + currentCustomer.getFirstName() + " is $" + currentAccount.getBalance());
        if (currentAccount instanceof CheckingAccount) {
            historyArea.append(". This is a Checking Account with overdraft protection $"
                    + ((CheckingAccount) currentAccount).getOverdraftAmount() + "\n");
        } else if (currentAccount instanceof SavingsAccount) {
            historyArea.append(". This is a Savings Account with interest rate "
                    + ((SavingsAccount) currentAccount).getInterestRate() + "%\n");
        }
        amountField.setText("");
    }

    public static void main(String[] args) {
        new BankATM();
    }

}