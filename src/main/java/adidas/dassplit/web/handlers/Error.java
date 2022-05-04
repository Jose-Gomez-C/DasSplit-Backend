package adidas.dassplit.web.handlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Error {
    @Getter
    @Setter
    private String field;
    @Getter
    @Setter
    private String message;

    public Error(String field, String message) {
        this.field = field;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error = (Error) o;
        return Objects.equals(field, error.field) &&
                Objects.equals(message, error.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, message);
    }
}