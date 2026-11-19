
package bank.model;

public class InvestmentAccount extends Account implements InterestCalculation {

    public static final double MINIMUM_OPENING_DEPOSIT = 500.00;
    private static final double RATE = 0.05;

    // Constructor
    public InvestmentAccount(Customer owner, String branch,
                             double openingDeposit) {

        super(owner, branch);

        // Check the minimum opening deposit
        if (!Double.isFinite(openingDeposit)
                || openingDeposit < MINIMUM_OPENING_DEPOSIT) {

            throw new IllegalArgumentException(
                "Investment account requires an opening deposit of at least USD 500"
            );
        }

        deposit(openingDeposit);
    }

    // Allow withdrawals if banking rules are satisfied
    @Override
    public boolean withdraw(double amount) {
        return performWithdrawal(amount);
    }

    // Calculate and credit monthly interest
    @Override
    public void payInterest() {
        creditInterest(getBalance() * RATE);
    }

    // Return the monthly interest rate
    @Override
    public double getInterestRate() {
        return RATE;
    }

    // Return the account type
    @Override
    public AccountType getType() {
        return AccountType.INVESTMENT;
    }
}
