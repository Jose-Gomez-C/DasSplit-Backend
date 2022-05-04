package adidas.dassplit.business.services.userservices.impl;

import adidas.dassplit.business.exception.AccountServiceException;
import adidas.dassplit.business.exception.UserServiceException;
import adidas.dassplit.business.model.Account;
import adidas.dassplit.business.model.Contribution;
import adidas.dassplit.business.model.User;
import adidas.dassplit.business.repositories.AccountRepository;
import adidas.dassplit.business.repositories.UserRepository;
import adidas.dassplit.business.services.userservices.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicesImpl implements UserServices {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public UserServicesImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public User createUser(User user) throws UserServiceException {
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            userRepository.save(user);
        } else {
            throw new UserServiceException(UserServiceException.USER_REPEATED_EXCEPTION);
        }
        return user;
    }

    @Override
    public Account createAccount(User user, Account account) {
        List<Account> accounts = user.getAccounts();
        accounts.add(account);
        user.setAccounts(accounts);
        return account;
    }

    @Override
    public Account addParticipant(Account account, User participant) {
        List<User> participants = account.getParticipants();
        participants.add(participant);
        account.setParticipants(participants);
        accountRepository.save(account);

        List<Account> accounts = participant.getAccounts();
        accounts.add(account);
        participant.setAccounts(accounts);
        userRepository.save(participant);
        return account;
    }

    @Override
    public Account addContribution(Account account, Contribution contribution) throws AccountServiceException {
        account.setCurrentDebt(account.getCurrentDebt()- contribution.getAmount());
        if(account.getCurrentDebt() < 0){
            throw new AccountServiceException(AccountServiceException.DEBET_CANNOT_NEGATIVE);
        }else{
            account.addContribution(contribution);
            accountRepository.save(account);
        }

        return account;

    }
    @Override
    public User findUserByEmail(String email) throws UserServiceException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserServiceException(UserServiceException.USER_NOT_FOUND_EXCEPTION));
    }
}
