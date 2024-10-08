package ca.bcit.comp2522.bank;

/**
 * The BankAccount class represents a bank account associated with a BankClient.
 * It includes functionalities to manage the account balance, track account
 * details, and perform withdrawals.
 *
 * <p>
 * Each bank account has a unique account number, an opening date, and an optional
 * closing date. The account can be accessed through a BankClient, allowing
 * withdrawals to be made using either just the amount or a PIN for security.
 * </p>
 *
 * <p>
 * The class provides methods to retrieve account details, including the balance,
 * account number, and dates related to the account's status.
 * </p>
 *
 */
public class BankAccount {

    private static final int    ACC_MIN_NUMBER = 6;
    private static final int    ACC_MAX_NUMBER = 7;
    private static final double ZERO_BALANCE   = 0;
    
    private final String    clientID;
    private final Date      accountOpened;
    private final Date      accountClosed;
    private final Person    client;
    private double          balanceUSD;
    private final int       pin;
    
    /**
     * Constructor method for BankAccount class.
     *
     * @param clientID Account number ({@value ACC_MIN_NUMBER} or {@value ACC_MAX_NUMBER} characters).
     * @param accountOpened The date when the account was opened.
     * @param accountClosed The date when the account was closed (if any).
     * @param client Client who owns the account.
     * @param balanceUSD Balance amount in USD.
     * @param pin The pin for the account.
     */
    public BankAccount(final String clientID, final Date accountOpened,
                       final Date accountClosed, final Person client,
                       final double balanceUSD, final int pin) {

        validateClientID(clientID);

        this.clientID       = clientID;
        this.accountOpened  = accountOpened;
        this.accountClosed  = accountClosed;
        this.client         = client;
        this.balanceUSD     = balanceUSD;
        this.pin            = pin;
    }

    /**
     * Overloaded Constructor for accounts which are not closed.
     * @param clientID Account number ({@value ACC_MIN_NUMBER} or {@value ACC_MAX_NUMBER} characters).
     * @param accountOpened The date when the account was opened.
     * @param client Client who owns the account.
     * @param balanceUSD Balance amount in USD.
     * @param pin The pin for the account.
     */
    public BankAccount(final String clientID, final Date accountOpened,
                       final Person client, final double balanceUSD, final int pin)
    {
        this(clientID, accountOpened, null, client, balanceUSD, pin);
    }

    /**
     * Checks if the account number is valid ({@value ACC_MIN_NUMBER} or {@value ACC_MAX_NUMBER} characters).
     *
     * @param clientID The account number to validate.
     * @throws IllegalArgumentException if null, less than  or more than {@value ACC_MAX_NUMBER} characters.
     */
    private static void validateClientID(final String clientID) {
        if (clientID == null ||
                clientID.length() < ACC_MIN_NUMBER ||
                clientID.length() > ACC_MAX_NUMBER) {
            throw new IllegalArgumentException("Account number must be" + ACC_MIN_NUMBER +
                                                       "or" + ACC_MAX_NUMBER + "characters.");
        }
    }

    /**
     * Withdraws a specified amount from the account.
     *
     * @param amountUSD The amount to withdraw in USD.
     */
    private void withdraw(final double amountUSD) {

        validateAmount(amountUSD);
        balanceUSD -= amountUSD;
    }

    /**
     * Validates the withdrawal amount.
     *
     * @param amountUSD The amount to withdraw in USD.
     * @throws IllegalArgumentException if amount is greater than the balance, or negative.
     */
    private void validateAmount(final double amountUSD) {
        if (amountUSD > balanceUSD) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal.");
        }

        if (amountUSD < ZERO_BALANCE) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    /**
     * Withdraws a specified amount from the account if the provided PIN matches.
     *
     * @param amountUsd The amount to withdraw in USD.
     * @param pinToMatch The PIN to verify the withdrawal.
     * @throws IllegalArgumentException if the PIN is incorrect.
     */
    public void withdraw(final double amountUsd, final int pinToMatch) {

        if (!(pin == pinToMatch)) {
            throw new IllegalArgumentException("Invalid PIN.");
        }

        withdraw(amountUsd);
    }

    /**
     * Gets the details of the bank account.
     *
     * @return A string containing the details of the account.
     */
    public String getDetails() {
        final StringBuilder sb;
        final String        details;
        
        sb = new StringBuilder();

        sb.append(client.getName().getFullName());
        sb.append(" had $");
        sb.append(balanceUSD);
        sb.append(" USD in account #");
        sb.append(clientID);
        sb.append(" which he opened on ");
        sb.append(accountOpened.getDayOfTheWeek());
        sb.append(" ");
        sb.append(accountOpened);

        if (accountClosed != null) {
            sb.append(" and closed ");
            sb.append(accountClosed.getDayOfTheWeek());
            sb.append(" ");
            sb.append(accountClosed);
            sb.append(".");
        } else {
            sb.append(" and is still open.");
        }

        details = sb.toString();
        return details;
    }

}
