package adidas.dassplit.business.exception;

public class AccountServiceException extends Exception {

    private static final long serialVersionUID = 1L;
    public static final String DEBET_CANNOT_NEGATIVE = "debt cannot be negative ";

    public AccountServiceException(String message) {
        super(message);
    }
}
