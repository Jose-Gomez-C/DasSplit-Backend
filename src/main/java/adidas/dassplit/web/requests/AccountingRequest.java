package adidas.dassplit.web.requests;

import adidas.dassplit.business.model.Accounting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
public class AccountingRequest {
    @Getter
    @Setter
    @Email
    @NotEmpty
    private String user;
    @Getter
    @Setter
    private long debit;
    @Setter
    @Getter
    private long balance;

    public Accounting toAccounting(){
        return new Accounting(user, debit, balance);
    }
}
