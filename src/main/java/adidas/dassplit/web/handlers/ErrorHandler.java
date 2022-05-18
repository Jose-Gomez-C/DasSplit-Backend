package adidas.dassplit.web.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ErrorHandler {

    public boolean isValidRequest(BindingResult result) {
        return !result.hasErrors();
    }

    public ResponseEntity<Object> getBadRequest(BindingResult result) {
        Map<String, List<Error>> response = new HashMap<>();
        List<Error> errors = result.getFieldErrors()
                .stream()
                .map(err -> new Error(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());
        response.put("errors", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
