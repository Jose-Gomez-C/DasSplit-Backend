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
public class Contribution implements Serializable {
    @Getter
    @Setter
    private User user;
    @Getter
    @Setter
    private Long amount;

}
