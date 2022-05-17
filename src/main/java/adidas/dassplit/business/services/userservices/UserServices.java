package adidas.dassplit.business.services.userservices;

import adidas.dassplit.business.exception.AccountServiceException;
import adidas.dassplit.business.exception.UserServiceException;
import adidas.dassplit.business.model.Account;
import adidas.dassplit.business.model.Accounting;
import adidas.dassplit.business.model.User;

import java.util.List;

public interface UserServices {
    User createUser(User user) throws UserServiceException;

    Account createAccount(User user, Account account) throws UserServiceException;

    Account addParticipant(String idAccount, String idParticipant) throws UserServiceException, AccountServiceException;

    Account addContribution(String idAccount, Accounting contribution) throws AccountServiceException, UserServiceException;

    long getAllCurretDebt(String idUser) throws UserServiceException, AccountServiceException;

    List<Account> getAllAccountByIdUser(String idUser) throws UserServiceException, AccountServiceException;

    boolean getUserExists(String idUser);

    User findUserByEmail(String email) throws UserServiceException;

    Account findAccountById(String idAccount) throws AccountServiceException;
}
