
package bank.model;

import java.time.LocalDateTime;

public class Transaction {

    public enum Type {
        DEPOSIT,
        WITHDRAWAL,
        INTEREST
    }

    private static int counter = 0;

    private final String transactionId;
    private final Type type;
    private final double amount;
    private final LocalDateTime date;

    public Transaction(Type type, double amount) {
        this.transactionId = String.format("TXN%06d", ++counter);
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Type getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format(
            "%s %-10s %s %.2f (%s)",
            transactionId,
            type,
            BankSettings.CURRENCY,
            amount,
            date.toLocalDate()
        );
    }
}
