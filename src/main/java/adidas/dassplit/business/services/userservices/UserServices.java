package adidas.dassplit.business.services.userservices;

import adidas.dassplit.business.exception.AccountServiceException;
import adidas.dassplit.business.exception.UserServiceException;
import adidas.dassplit.business.model.Account;
import adidas.dassplit.business.model.Contribution;
import adidas.dassplit.business.model.User;

public interface UserServices {
    User createUser(User user) throws UserServiceException;

    Account createAccount(User user, Account account);

    Account addParticipant(Account account, User participant);

    Account addContribution(Account account, Contribution contribution) throws AccountServiceException;

    User findUserByEmail(String email) throws UserServiceException;
}
