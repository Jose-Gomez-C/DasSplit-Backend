package adidas.dassplit.web.requests;

import adidas.dassplit.business.model.Account;
import adidas.dassplit.business.model.Accounting;
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
    private List<String> participants;
    @Getter
    @Setter
    private List<Accounting> statement;
    @Getter
    @Setter
    private Boolean isFavorite;
    @Getter
    @Setter
    private List<Accounting> contributions;

    public Account toAccount(AccountRequest accountRequest) {
        return new Account(name, totalDebt, currentDebt, participants, contributions, statement, isFavorite);
    }
}
