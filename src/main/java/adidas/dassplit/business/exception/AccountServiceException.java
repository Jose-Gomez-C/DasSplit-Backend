package adidas.dassplit.business.exception;

public class AccountServiceException extends Exception {

    private static final long serialVersionUID = 1L;
    public static final String DEBET_CANNOT_NEGATIVE = "debt cannot be negative ";
    public static final String ACCOUNT_NOT_FOUND = "Account not found";
    public static final String ACCOUNT_REPEATED_EXCEPTION = "Account repeated";

    public AccountServiceException(String message) {
        super(message);
    }
}
