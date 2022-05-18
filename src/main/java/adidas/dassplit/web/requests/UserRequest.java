package adidas.dassplit.web.requests;


import adidas.dassplit.business.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Getter
    @Setter
    private String name;
    @Email
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private List<String> accounts;
    @Getter
    @Setter
    private List<User> friends;

    public UserRequest(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.accounts = user.getAccounts();
        this.friends = user.getFriends();
    }

    public User toUser() {
        return new User(name, email.toLowerCase(), accounts, friends);
    }

}
