package adidas.dassplit.business.repositories;

import adidas.dassplit.business.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

}
