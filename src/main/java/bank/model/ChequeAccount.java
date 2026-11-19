
package bank.model;

public class ChequeAccount extends Account {

    private final String employerName;
    private final String employerAddress;

    // Constructor
    public ChequeAccount(Customer owner, String branch,
                         String employerName, String employerAddress) {

        super(owner, branch);

        // Only individual customers can open Cheque accounts
        if (!(owner instanceof IndividualCustomer)) {
            throw new IllegalArgumentException(
                "Only individual customers can open Cheque accounts"
            );
        }

        // Employer information is required
        if (employerName == null || employerName.isBlank()
                || employerAddress == null || employerAddress.isBlank()) {

            throw new IllegalArgumentException(
                "Employer name and address are required"
            );
        }

        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    // Withdraw money according to the banking rules
    @Override
    public boolean withdraw(double amount) {
        return performWithdrawal(amount);
    }

    // Return the account type
    @Override
    public AccountType getType() {
        return AccountType.CHEQUE;
    }

    // Getter methods
    public String getEmployerName() {
        return employerName;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }
}
