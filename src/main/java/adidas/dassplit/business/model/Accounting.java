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


}
