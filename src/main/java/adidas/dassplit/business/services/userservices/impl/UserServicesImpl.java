package adidas.dassplit.business.services.userservices.impl;

import adidas.dassplit.business.exception.AccountServiceException;
import adidas.dassplit.business.exception.UserServiceException;
import adidas.dassplit.business.model.Account;
import adidas.dassplit.business.model.Accounting;
import adidas.dassplit.business.model.User;
import adidas.dassplit.business.repositories.AccountRepository;
import adidas.dassplit.business.repositories.UserRepository;
import adidas.dassplit.business.services.userservices.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Account createAccount(User user, Account account) throws UserServiceException {
        account.setCurrentDebt(account.getTotalDebt()/ account.getParticipants().size());
        modifyStatement(account);
        accountRepository.save(account);
        user.addAccounts(account.getId());
        List<String> participants =  account.getParticipants();
        for (String i : participants){
            if (!(user.getEmail().equals(i))){
                User participant = userRepository.findByEmail(i).orElseThrow(() -> new UserServiceException(UserServiceException.USER_NOT_FOUND_EXCEPTION));
                participant.addAccounts(account.getId());
                userRepository.save(participant);
            }
        }
        userRepository.save(user);
        return account;
    }
    private void modifyStatement(Account account){
        Map<String,Accounting> accounted = new HashMap<>();
        List<Accounting> statement = new ArrayList<>();
        for(Accounting i : account.getAccountings()){
            if(accounted.containsKey(i.getUser())){

                Accounting accounting = accounted.get(i.getUser());
                accounting.setDebit(accounting.getDebit()+ i.getDebit());
                accounting.setBalance(accounting.getBalance()+i.getDebit());
            }else{
                accounted.put(i.getUser(), new Accounting(i.getUser(), i.getDebit(), (account.getCurrentDebt()*-1)+i.getDebit()));
            }
        }
        for(String i : account.getParticipants()){
            if(!(accounted.containsKey(i))){
                accounted.put(i, new Accounting(i,0,account.getCurrentDebt()*-1));
            }
        }
        for (Map.Entry<String, Accounting> entry : accounted.entrySet()) {
            statement.add(entry.getValue());
        }

        account.setStatement(statement);
    }

    @Override
    public Account addParticipant(String idAccount, String idParticipant) throws UserServiceException, AccountServiceException {
        Account account = accountRepository.findById(idAccount).orElseThrow(() -> new AccountServiceException(AccountServiceException.ACCOUNT_NOT_FOUND));
        User participant = userRepository.findByEmail(idParticipant).orElseThrow(() -> new UserServiceException(UserServiceException.USER_NOT_FOUND_EXCEPTION));
        List<Accounting> participants = account.getAccountings();
        Accounting accounting = new Accounting(participant.getEmail(), 0, 0);
        participants.add(accounting);
        account.setAccountings(participants);
        accountRepository.save(account);
        List<String> accounts = participant.getAccounts();
        accounts.add(account.getId());
        participant.setAccounts(accounts);
        userRepository.save(participant);
        return account;
    }

    @Override
    public Account addContribution(String idAccount, Accounting contribution) throws AccountServiceException, UserServiceException {
        Account account = accountRepository.findById(idAccount).orElseThrow(() -> new UserServiceException(AccountServiceException.ACCOUNT_NOT_FOUND));
        account.setCurrentDebt(account.getCurrentDebt() - contribution.getDebit());
        if (account.getCurrentDebt() < 0) {
            throw new AccountServiceException(AccountServiceException.DEBET_CANNOT_NEGATIVE);
        } else if (contribution.getBalance() > 0) {
            account.setTotalDebt(account.getTotalDebt()+ contribution.getBalance());
            account.setCurrentDebt(account.getCurrentDebt()+ contribution.getBalance());
        } else {
            account.addContribution(contribution);
            accountRepository.save(account);
        }
        return account;
    }
    @Override
    public long getAllCurretDebt(String idUser) throws UserServiceException, AccountServiceException {
        User user = userRepository.findByEmail(idUser).orElseThrow(() -> new UserServiceException(UserServiceException.USER_NOT_FOUND_EXCEPTION));
        List<String> accounts = user.getAccounts();
        long totalDeb = 0;
        for (String i : accounts){
            Account account = accountRepository.findById(i).orElseThrow(() -> new AccountServiceException(AccountServiceException.ACCOUNT_NOT_FOUND));
            List<Accounting> accountings = account.getAccountings();
            for (Accounting y : accountings){
                if (user.getEmail().equals(y.getUser())){
                    totalDeb += y.getBalance();
                }
            }
        }
        return totalDeb;
    }
    @Override
    public List<Account> getAllAccountByIdUser(String idUser) throws UserServiceException, AccountServiceException {
        User user = userRepository.findByEmail(idUser).orElseThrow(() -> new UserServiceException(UserServiceException.USER_NOT_FOUND_EXCEPTION));
        List<Account> accounts = new ArrayList<>();
        for (String account : user.getAccounts()){
            accounts.add(accountRepository.findById(account).orElseThrow(() -> new AccountServiceException(AccountServiceException.ACCOUNT_NOT_FOUND)));
        }
        return accounts;
    }
    @Override
    public boolean getUserExists(String idUser){
        boolean userExists;
        try {
            findUserByEmail(idUser);
            userExists = true;
        } catch (UserServiceException e) {
            userExists = false;
        }
        return userExists;
    }

    @Override
    public User findUserByEmail(String email) throws UserServiceException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserServiceException(UserServiceException.USER_NOT_FOUND_EXCEPTION));
    }
    @Override
    public Account findAccountById(String idAccount) throws AccountServiceException{
        return accountRepository.findById(idAccount).orElseThrow(() -> new AccountServiceException(AccountServiceException.ACCOUNT_NOT_FOUND));
    }
}
