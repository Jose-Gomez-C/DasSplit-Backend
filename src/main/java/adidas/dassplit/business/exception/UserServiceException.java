package adidas.dassplit.business.exception;

import java.util.function.Supplier;

public class UserServiceException  extends Exception {

    private static final long serialVersionUID = 1L;
    public static final String USER_REPEATED_EXCEPTION = "User repeated";
    public static final String USER_NOT_FOUND_EXCEPTION = "User not found";
    public UserServiceException(String message){
        super(message);
    }

}
