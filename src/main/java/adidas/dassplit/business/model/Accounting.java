package adidas.dassplit.business.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "contributions")
@NoArgsConstructor
@AllArgsConstructor
public class Accounting implements Serializable {
    @Getter
    @Setter
    private String user;
    @Getter
    @Setter
    private long debit;
    @Getter
    @Setter
    private long balance;
    @Getter
    @Setter
    private String name;


    public Accounting(String user, int debit, long balance) {
        this.user = user;
        this.debit = debit;
        this.balance = balance;
        this.name = "N/A";
    }
}
