package adidas.dassplit.web.controllers;

import adidas.dassplit.business.exception.AccountServiceException;
import adidas.dassplit.business.exception.UserServiceException;
import adidas.dassplit.business.model.Account;
import adidas.dassplit.business.model.User;
import adidas.dassplit.business.services.userservices.UserServices;
import adidas.dassplit.web.handlers.ErrorHandler;
import adidas.dassplit.web.requests.AccountingRequest;
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
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
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

    @GetMapping("{idUser}")
    public ResponseEntity<Object> getUser(@PathVariable String idUser) {
        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(userServices.findUserByEmail(idUser), HttpStatus.OK);
        } catch (UserServiceException e) {
            Map<String, String> error = new HashMap<>();
            error.put(key, e.getMessage());
            responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("{idUser}/account")
    public ResponseEntity<Object> getAllAccounts(@PathVariable String idUser) {
        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(userServices.getAllAccountByIdUser(idUser), HttpStatus.OK);
        } catch (UserServiceException | AccountServiceException e) {
            Map<String, String> error = new HashMap<>();
            error.put(key, e.getMessage());
            responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("{idUser}/exists")
    public ResponseEntity<Object> getUserExists(@PathVariable String idUser) {
        return new ResponseEntity<>(userServices.getUserExists(idUser), HttpStatus.OK);
    }

    @GetMapping("{idUser}/debtStatus")
    public ResponseEntity<Object> getAllCurretDebt(@PathVariable String idUser) {
        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(userServices.getAllCurretDebt(idUser), HttpStatus.OK);
        } catch (UserServiceException | AccountServiceException e) {
            Map<String, String> error = new HashMap<>();
            error.put(key, e.getMessage());
            responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("/account/{idAccount}")
    public ResponseEntity<Object> getAccount(@PathVariable String idAccount) {
        System.out.println("hola");
        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(userServices.findAccountById(idAccount), HttpStatus.OK);
        } catch (AccountServiceException e) {
            Map<String, String> error = new HashMap<>();
            error.put(key, e.getMessage());
            responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }
        return responseEntity;
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

    @PostMapping("{emailUser}/accounts")
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

    @PutMapping("accounts/{idAccount}/{idParticipant}")
    public ResponseEntity<Object> addParticipant(@PathVariable String idParticipant, @PathVariable String idAccount) {
        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(userServices.addParticipant(idAccount, idParticipant), HttpStatus.ACCEPTED);
        } catch (UserServiceException | AccountServiceException e) {
            Map<String, String> error = new HashMap<>();
            error.put(key, e.getMessage());
            responseEntity = new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @PutMapping("/accounts/{idAccount}")
    public ResponseEntity<Object> addContribution(@PathVariable String idAccount, @Valid @RequestBody AccountingRequest accountingRequest, BindingResult result) {
        ResponseEntity<Object> responseEntity;
        if (errorHandler.isValidRequest(result)) {
            try {
                Account account = userServices.addContribution(idAccount, accountingRequest.toAccounting());
                responseEntity = new ResponseEntity<>(account, HttpStatus.ACCEPTED);

            } catch (UserServiceException | AccountServiceException e) {
                Map<String, String> error = new HashMap<>();
                error.put(key, e.getMessage());
                responseEntity = new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }

        } else {
            responseEntity = errorHandler.getBadRequest(result);
        }
        return responseEntity;

    }

}
