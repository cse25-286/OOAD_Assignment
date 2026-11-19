
package bank;

import bank.model.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== BANKING SYSTEM ===");

        // Create an individual customer
        IndividualCustomer thato = new IndividualCustomer(
            "Hope", "Molapisi", "123456789",
            "Larona Molapisi", "Plot 12, Gaborone", "71234567"
        );

        // Create a company customer
        CompanyCustomer company = new CompanyCustomer(
            "Builders Market", "BW00001234",
            "Plot 123, Gaborone", "72345678"
        );

        company.addShareholder(
            new Shareholder("Yenkosi Dube", "987654321", "73456789")
        );

        System.out.println("\n=== CUSTOMERS ===");
        System.out.println(thato.getCustomerId() + " " + thato.getDisplayName());
        System.out.println(company.getCustomerId() + " " + company.getDisplayName());

        // Open accounts
        System.out.println("\n=== OPENING ACCOUNTS ===");

        Account savings = thato.openAccount(AccountType.SAVINGS, 200);
        Account investment = thato.openAccount(AccountType.INVESTMENT, 1000);
        Account cheque = thato.openAccount(
            AccountType.CHEQUE, 300,
            "Botswana Power Corp", "Gaborone"
        );

        company.openAccount(AccountType.SAVINGS, 0);

        for (Account account : thato.getAccounts()) {
            System.out.println(account);
        }

        // Test banking rules
        System.out.println("\n=== BANKING RULE TESTS ===");

        try {
            company.openAccount(AccountType.INVESTMENT, 100);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        try {
            company.openAccount(AccountType.CHEQUE, 0, "ABC", "Gaborone");
        } catch (IllegalStateException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        try {
            thato.openAccount(AccountType.SAVINGS, 50);
        } catch (IllegalStateException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        try {
            savings.deposit(-20);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        // Demonstrate polymorphism
        System.out.println("\n=== WITHDRAWALS ===");

        for (Account account : thato.getAccounts()) {
            boolean success = account.withdraw(100);

            System.out.println(
                account.getType() + ": " + success +
                " - " + account.getLastMessage()
            );
        }

        // Test daily withdrawal limit
        System.out.println("\n=== DAILY WITHDRAWAL LIMIT ===");

        investment.deposit(10000);

        System.out.println(
            "Withdraw 4000: " + investment.withdraw(4000)
        );

        System.out.println(
            "Withdraw 2000: " + investment.withdraw(2000)
            + " - " + investment.getLastMessage()
        );

        // Calculate interest
        System.out.println("\n=== MONTHLY INTEREST ===");

        Account[] accounts = {savings, investment, cheque};

        for (Account account : accounts) {

            if (account instanceof InterestCalculation) {

                InterestCalculation interestAccount =
                    (InterestCalculation) account;

                interestAccount.payInterest();

                System.out.println(
                    account.getType() +
                    " interest rate: " +
                    interestAccount.getInterestRate()
                );
            }
        }

        // Display balances
        System.out.println("\n=== ACCOUNT BALANCES ===");

        for (Account account : accounts) {
            System.out.println(account);
        }

        // Display transaction history
        System.out.println("\n=== TRANSACTION HISTORY ===");

        for (Transaction transaction : investment.getTransactions()) {
            System.out.println(transaction);
        }

        // Test login
        System.out.println("\n=== LOGIN TEST ===");

        User user = new User("thato", "secret123", "CUSTOMER");

        System.out.println(
            "Correct password: " + user.authenticate("secret123")
        );

        System.out.println(
            "Wrong password 1: " + user.authenticate("wrong1")
        );

        System.out.println(
            "Wrong password 2: " + user.authenticate("wrong2")
        );

        System.out.println(
            "Wrong password 3: " + user.authenticate("wrong3")
        );

        System.out.println("Account locked: " + user.isLocked());

        user.unlock();

        System.out.println("Account unlocked: " + !user.isLocked());

        System.out.println("\n=== END OF TEST ===");
    }
}

