
package bank.model;

public class IndividualCustomer extends Customer {

    private final String firstName;
    private final String surname;
    private final String omang;
    private final String nextOfKin;

    // Constructor
    public IndividualCustomer(String firstName, String surname,
                              String omang, String nextOfKin,
                              String physicalAddress, String contactNumber) {

        super(physicalAddress, contactNumber);

        this.firstName = firstName;
        this.surname = surname;
        this.omang = omang;
        this.nextOfKin = nextOfKin;
    }

    // Check which account types an individual can hold
    @Override
    public boolean canHold(AccountType type) {
        return type != null;
    }

    // Return the customer's full name
    @Override
    public String getDisplayName() {
        return firstName + " " + surname;
    }

    // Getter methods
    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getOmang() {
        return omang;
    }

    public String getNextOfKin() {
        return nextOfKin;
    }
}
