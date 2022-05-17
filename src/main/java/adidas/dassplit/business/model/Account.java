package adidas.dassplit.business.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "accounts")
@NoArgsConstructor

public class Account implements Serializable {
    @Id
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String name;
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
    private List<Accounting> accountings;
    @Getter
    @Setter
    private List<Accounting> statement;
    @Getter
    @Setter
    private Boolean isFavorite;


    public Account(String name, long totalDebt, long currentDebt, List<String> participants, List<Accounting> accountings, List<Accounting> statement, Boolean isFavorite) {
        this.name = name;
        this.totalDebt = totalDebt;
        this.currentDebt = currentDebt;
        this.participants = participants;
        this.accountings = accountings;
        this.isFavorite = isFavorite;
        this.statement = statement;
    }
    public void addContribution(Accounting contribution){
        accountings.add(contribution);
    }

}
