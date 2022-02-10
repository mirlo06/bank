package com.zmerli.bank.exposition.endpoint;

import com.zmerli.bank.domain.entity.Account;
import com.zmerli.bank.domain.entity.Operation;
import com.zmerli.bank.domain.manager.AccountManager;
import com.zmerli.bank.domain.manager.OperationManager;
import com.zmerli.bank.domain.service.AccountService;
import com.zmerli.bank.exposition.dto.v1.operation.OperationRequestV1;
import com.zmerli.bank.exposition.dto.v1.operation.OperationV1DTO;
import com.zmerli.bank.exposition.execption.model.AccountNotFoundException;
import com.zmerli.bank.exposition.execption.model.InsufficientFundException;
import com.zmerli.bank.exposition.execption.model.ResourceNotFoundException;
import com.zmerli.bank.exposition.mapper.OperationMapperV1;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/operations")
@Api(value = "Operation API V1", tags = "Operation")
@Slf4j
public class OperationApiV1 {

    @Autowired
    private OperationManager operationManager;


    @Autowired
    private AccountManager accountManager;

    @Autowired
    private OperationMapperV1 operationMapperV1;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 201, message = "Operation created", response = OperationV1DTO.class)
    @CrossOrigin(origins = "*")
    public ResponseEntity<OperationV1DTO> saveOperation(@Validated @RequestBody OperationRequestV1 operationRequestV1) throws InsufficientFundException, AccountNotFoundException {

        log.info(String.format("saveOperation() newOperationRequest for Client Number: %s , Operation Amount: %s", operationRequestV1.getAccountNumber(), operationRequestV1.getOperationAmount()));

        Account account = accountManager.getAccount(operationRequestV1.getAccountNumber());

        if (Objects.equals(account, null)) {
            throw new AccountNotFoundException(operationRequestV1.getAccountNumber());
        }

        if (!checkAccountBalance(account, operationRequestV1.getOperationAmount())) {
            throw new InsufficientFundException(operationRequestV1.getAccountNumber());
        }
        accountManager.updateAccountBalance(account, operationRequestV1.getOperationAmount());
        log.info("Account {} position updated", account.getAccountNumber());
        Operation operation = operationManager.saveOperation(account, operationRequestV1.getOperationAmount());
        log.info("New Operation registered for account {}", account.getAccountNumber());

        return new ResponseEntity<>(operationMapperV1.toOperationDTOV1(operation), HttpStatus.CREATED);
    }

    boolean checkAccountBalance(Account account, Double amount) {

        return account.getAccountBalance() + amount > 0;
    }


    @GetMapping(value = "/{operationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "Operation found", response = OperationV1DTO.class)
    @CrossOrigin(origins = "*")
    public OperationV1DTO getOperationInfo(
            @PathVariable("operationId") final Long operationId) throws ResourceNotFoundException {
        log.info("getOperationInfo() operationId: {}", operationId);
        Operation operation = operationManager.getOperation(operationId);
        if (Objects.equals(operation, null)) {
            throw new ResourceNotFoundException(operationId);
        }
        return operationMapperV1.toOperationDTOV1(operation);
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "Operations found", response = List.class)
    @CrossOrigin(origins = "*")
    public List<OperationV1DTO> getAllOperationForAClient(
            @RequestParam(value = "accountNumber") int accountNumber,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) throws AccountNotFoundException {

        log.info("getAllOperationForAClient() accountNumber: {}", accountNumber);

        Account account = accountManager.getAccount(accountNumber);
        if (Objects.equals(account, null)) {
            throw new AccountNotFoundException(accountNumber);
        }


        return operationMapperV1.toListOperationDTOV1(operationManager.getAllOperationByAccount(account, page, size));
    }

}
