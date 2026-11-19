
package bank.model;

public class Shareholder {

    private final String name;
    private final String omang;
    private final String contactNumber;

    public Shareholder(String name, String omang, String contactNumber) {
        this.name = name;
        this.omang = omang;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public String getOmang() {
        return omang;
    }

    public String getContactNumber() {
        return contactNumber;
    }
}
