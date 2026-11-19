
package bank.model;

import java.util.ArrayList;
import java.util.List;

public class CompanyCustomer extends Customer {

    private final String companyName;
    private final String registrationNumber;

    private final List<Shareholder> shareholders = new ArrayList<>();

    // Constructor
    public CompanyCustomer(String companyName, String registrationNumber,
                           String physicalAddress, String contactNumber) {

        super(physicalAddress, contactNumber);

        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
    }

    // Companies can only open Savings and Investment accounts
    @Override
    public boolean canHold(AccountType type) {
        return type == AccountType.SAVINGS
            || type == AccountType.INVESTMENT;
    }

    // Return the company's name
    @Override
    public String getDisplayName() {
        return companyName;
    }

    // Add a shareholder
    public void addShareholder(Shareholder shareholder) {

        if (shareholder == null) {
            throw new IllegalArgumentException(
                "Shareholder cannot be null"
            );
        }

        shareholders.add(shareholder);
    }

    // Return a copy of the shareholder list
    public List<Shareholder> getShareholders() {
        return new ArrayList<>(shareholders);
    }

    // Getter methods
    public String getCompanyName() {
        return companyName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
}
