
package bank.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Account {

    private static int counter = 1000;

    private final String accountNumber;
    private final String branch;
    private final Customer owner;

    private double balance;
    private final List<Transaction> transactions = new ArrayList<>();
    private String lastMessage = "";

    protected Account(Customer owner, String branch) {
        if (owner == null) {
            throw new IllegalArgumentException(
                "An account must have an owner"
            );
        }

        this.owner = owner;
        this.branch = branch;
        this.accountNumber = "ACC" + (++counter);
    }

    public void deposit(double amount) {
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException(
                "Deposit amount must be a valid positive number"
            );
        }

        if (!Double.isFinite(balance + amount)) {
            throw new IllegalArgumentException(
                "Deposit would exceed the allowed balance"
            );
        }

        balance += amount;

        transactions.add(
            new Transaction(Transaction.Type.DEPOSIT, amount)
        );

        lastMessage = "Deposit successful";
    }

    public void deposit(double amount, String note) {
        deposit(amount);
        if (note != null && !note.isBlank()) {
            lastMessage = note;
        }
    }

    public abstract boolean withdraw(double amount);

    protected boolean performWithdrawal(double amount) {
        if (!Double.isFinite(amount) || amount <= 0) {
            lastMessage = "Withdrawal amount must be a valid positive number";
            return false;
        }

        if (amount > balance) {
            lastMessage = "Insufficient funds";
            return false;
        }

        double limit = BankSettings.getInstance()
                                   .getDailyWithdrawalLimit();

        if (owner.getWithdrawnToday() + amount > limit) {
            lastMessage = String.format(
                "Daily withdrawal limit of %s %.2f exceeded",
                BankSettings.CURRENCY,
                limit
            );
            return false;
        }

        balance -= amount;

        transactions.add(
            new Transaction(Transaction.Type.WITHDRAWAL, amount)
        );

        lastMessage = "Withdrawal successful";
        return true;
    }

    protected void creditInterest(double interest) {
        if (!Double.isFinite(interest) || interest <= 0) {
            return;
        }

        if (!Double.isFinite(balance + interest)) {
            throw new IllegalArgumentException(
                "Interest would exceed the allowed balance"
            );
        }

        balance += interest;

        transactions.add(
            new Transaction(Transaction.Type.INTEREST, interest)
        );
    }

    protected void setLastMessage(String message) {
        this.lastMessage = message;
    }

    public abstract AccountType getType();

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getBranch() {
        return branch;
    }

    public Customer getOwner() {
        return owner;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    @Override
    public String toString() {
        return String.format(
            "%s | %s | %s | Balance: %s %.2f",
            accountNumber,
            getType(),
            owner.getDisplayName(),
            BankSettings.CURRENCY,
            balance
        );
    }
}
