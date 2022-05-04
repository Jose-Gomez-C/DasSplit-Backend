package adidas.dassplit.business.model;

import lombok.AllArgsConstructor;
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
    private List<Contribution> contributions;
    @Getter
    @Setter
    private Boolean isFavorite;
    @Getter
    @Setter
    private List<User> participants;

    public Account(String name, long totalDebt, long currentDebt, List<Contribution> contributions, Boolean isFavorite, List<User> participants) {
        this.name = name;
        this.totalDebt = totalDebt;
        this.currentDebt = currentDebt;
        this.contributions = contributions;
        this.isFavorite = isFavorite;
        this.participants = participants;
    }
    public void addContribution(Contribution contribution){
        contributions.add(contribution);
    }
}
