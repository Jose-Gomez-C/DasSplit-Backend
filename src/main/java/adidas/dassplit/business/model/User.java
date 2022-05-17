package adidas.dassplit.business.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Getter
    @Setter
    private String name;
    @Id
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private List<String> accounts;
    @Getter
    @Setter
    private List<User> friends;

    public void addAccounts(String account){
        accounts.add(account);
    }
}
