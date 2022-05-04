package adidas.dassplit.web.requests;

import adidas.dassplit.business.model.Account;
import adidas.dassplit.business.model.Contribution;
import adidas.dassplit.business.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@NoArgsConstructor
public class AccountRequest {
    @NotEmpty
    @Getter
    @Setter
    private String name;
    @NotEmpty
    @Getter
    @Setter
    private long totalDebt;
    @Getter
    @Setter
    private long currentDebt;
    @Getter
    @Setter
    private List<Contribution> contributions;
    @Getter
    @Setter
    private Boolean isFavorite;
    @Getter
    @Setter
    private List<User> participants;

    public Account toAccount(AccountRequest accountRequest ){
        return new Account(name, totalDebt, currentDebt, contributions, isFavorite, participants);
    }
}
