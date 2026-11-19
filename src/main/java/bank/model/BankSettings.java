
package bank.model;

public class BankSettings {

    public static final String CURRENCY = "USD";
    public static final String DEFAULT_BRANCH = "Main Branch";

    private static final BankSettings INSTANCE = new BankSettings();

    private double dailyWithdrawalLimit = 5000.00;

    private BankSettings() {
    }

    public static BankSettings getInstance() {
        return INSTANCE;
    }

    public double getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public void setDailyWithdrawalLimit(double limit) {
        if (!Double.isFinite(limit) || limit <= 0) {
            throw new IllegalArgumentException(
                "Limit must be a positive, finite number"
            );
        }

        this.dailyWithdrawalLimit = limit;
    }
}
