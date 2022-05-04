package adidas.dassplit.web.controllers;

import adidas.dassplit.business.exception.UserServiceException;
import adidas.dassplit.business.model.Account;
import adidas.dassplit.business.model.User;
import adidas.dassplit.business.services.userservices.UserServices;
import adidas.dassplit.web.handlers.ErrorHandler;
import adidas.dassplit.web.requests.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {
    private final ErrorHandler errorHandler;
    private final UserServices userServices;
    private final String key;

    @Autowired
    public UserController(UserServices userServices) {
        errorHandler = new ErrorHandler();
        this.userServices = userServices;
        key = "Error";
    }

    @PostMapping()
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserRequest userRequest, BindingResult result) {
        User user = userRequest.toUser();
        ResponseEntity<Object> responseEntity;
        if (errorHandler.isValidRequest(result)) {
            try {
                userServices.createUser(user);
                responseEntity = new ResponseEntity<>(user, HttpStatus.CREATED);
            } catch (UserServiceException e) {
                Map<String, String> error = new HashMap<>();
                error.put(key, e.getMessage());
                responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
            }

        } else {
            responseEntity = errorHandler.getBadRequest(result);
        }
        return responseEntity;

    }

    @PostMapping("{emailUser}accounts/")
    public ResponseEntity<Object> createAccount(@Valid @RequestBody Account account, @PathVariable String emailUser, BindingResult result) {
        ResponseEntity<Object> responseEntity;
        if (errorHandler.isValidRequest(result)) {
            try {
                User user = userServices.findUserByEmail(emailUser);

                responseEntity = new ResponseEntity<>(userServices.createAccount(user, account), HttpStatus.CREATED);
            } catch (UserServiceException e) {
                Map<String, String> error = new HashMap<>();
                error.put(key, e.getMessage());
                responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
            }
        } else {
            responseEntity = errorHandler.getBadRequest(result);
        }
        return responseEntity;
    }

}
