
package bank.controller;

import bank.model.Account;
import bank.model.AccountType;
import bank.model.Customer;

public class DashboardController {

    private Customer customer;
    private Account selectedAccount;

    // Set the customer using the dashboard
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // Display the customer's name
    public String getWelcomeMessage() {
        return "Welcome, " + customer.getDisplayName() + "!";
    }

    // Count the customer's accounts
    public int getNumberOfAccounts() {
        return customer.getAccounts().size();
    }

    // Select an account
    public void selectAccount(AccountType type) {

        selectedAccount = null;

        for (Account account : customer.getAccounts()) {
            if (account.getType() == type) {
                selectedAccount = account;
                break;
            }
        }
    }

    // Display the selected account balance
    public double getAvailableBalance() {
        if (selectedAccount == null) {
            return 0;
        }

        return selectedAccount.getBalance();
    }

    // Deposit money
    public String deposit(double amount) {

        if (selectedAccount == null) {
            return "Please select an account.";
        }

        try {
            selectedAccount.deposit(amount);
            return "Deposit successful.";

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // Withdraw money
    public String withdraw(double amount) {

        if (selectedAccount == null) {
            return "Please select an account.";
        }

        boolean success = selectedAccount.withdraw(amount);

        if (success) {
            return "Withdrawal successful.";
        }

        return selectedAccount.getLastMessage();
    }

    // Check if withdrawal should be disabled
    public boolean isWithdrawalDisabled() {

        if (selectedAccount == null) {
            return true;
        }

        return selectedAccount.getType() == AccountType.SAVINGS;
    }
}
