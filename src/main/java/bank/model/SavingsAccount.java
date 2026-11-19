
package bank.model;

public class SavingsAccount extends Account implements InterestCalculation {

    private static final double RATE = 0.0005;

    // Constructor
    public SavingsAccount(Customer owner, String branch) {
        super(owner, branch);
    }

    // Savings accounts do not allow withdrawals
    @Override
    public boolean withdraw(double amount) {
        setLastMessage("Withdrawals are not allowed from a Savings account");
        return false;
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
        return AccountType.SAVINGS;
    }
}
