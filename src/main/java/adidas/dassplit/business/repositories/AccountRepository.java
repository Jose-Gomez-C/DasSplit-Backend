package adidas.dassplit.business.repositories;

import adidas.dassplit.business.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findById(String idAccount);
}
