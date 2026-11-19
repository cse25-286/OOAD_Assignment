
package bank.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Customer {

    private static int counter = 0;

    private final String customerId;
    private String physicalAddress;
    private String contactNumber;

    private final List<Account> accounts = new ArrayList<>();

    // Constructor
    protected Customer(String physicalAddress, String contactNumber) {
        this.customerId = String.format("CUS%04d", ++counter);
        this.physicalAddress = physicalAddress;
        this.contactNumber = contactNumber;
    }

    // Abstract methods
    public abstract boolean canHold(AccountType type);

    public abstract String getDisplayName();

    // Opens a Savings or Investment account
    public Account openAccount(AccountType type, double openingDeposit) {
        return openAccount(type, openingDeposit, null, null);
    }

    // Opens an account, including Cheque accounts
    public Account openAccount(AccountType type, double openingDeposit,
                               String employerName, String employerAddress) {

        // Check account type
        if (type == null) {
            throw new IllegalArgumentException(
                "Please select an account type"
            );
        }

        // Check opening deposit
        if (!Double.isFinite(openingDeposit) || openingDeposit < 0) {
            throw new IllegalArgumentException(
                "Opening deposit must be a valid non-negative amount"
            );
        }

        // Check if customer is allowed to hold this account
        if (!canHold(type)) {
            throw new IllegalStateException(
                getDisplayName() + " cannot hold a " + type + " account"
            );
        }

        // Prevent duplicate account types
        if (hasAccountOfType(type)) {
            throw new IllegalStateException(
                "Customer already holds a " + type + " account"
            );
        }

        Account account;

        // Create the selected account
        switch (type) {

            case SAVINGS:
                account = new SavingsAccount(
                    this, BankSettings.DEFAULT_BRANCH
                );

                if (openingDeposit > 0) {
                    account.deposit(openingDeposit);
                }
                break;

            case INVESTMENT:
                account = new InvestmentAccount(
                    this, BankSettings.DEFAULT_BRANCH, openingDeposit
                );
                break;

            case CHEQUE:
                account = new ChequeAccount(
                    this, BankSettings.DEFAULT_BRANCH,
                    employerName, employerAddress
                );

                if (openingDeposit > 0) {
                    account.deposit(openingDeposit);
                }
                break;

            default:
                throw new IllegalArgumentException(
                    "Unknown account type"
                );
        }

        accounts.add(account);
        return account;
    }

    // Check if customer already has this account type
    public boolean hasAccountOfType(AccountType type) {

        for (Account account : accounts) {
            if (account.getType() == type) {
                return true;
            }
        }

        return false;
    }

    // Calculate total withdrawals made today
    public double getWithdrawnToday() {

        LocalDate today = LocalDate.now();
        double total = 0;

        for (Account account : accounts) {

            for (Transaction transaction : account.getTransactions()) {

                if (transaction.getType() == Transaction.Type.WITHDRAWAL
                        && transaction.getDate().toLocalDate().equals(today)) {

                    total += transaction.getAmount();
                }
            }
        }

        return total;
    }

    // Getter methods
    public String getCustomerId() {
        return customerId;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    // Setter methods
    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    // Return a copy of the customer's account list
    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }
}
