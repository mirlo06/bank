package com.zmerli.bank.domain.manager;


import com.zmerli.bank.domain.entity.Account;
import com.zmerli.bank.domain.entity.Operation;
import com.zmerli.bank.domain.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperationManager {

    @Autowired
    private OperationService operationService;

    public Operation saveOperation(Account account, Double operationValue) {
        return operationService.saveOperation(account, operationValue);
    }

    public List<Operation> getAllOperationByAccount(Account account, int page, int size) {
        return operationService.getAllOperationByAccount(account, page, size);
    }

    public Operation getOperation(Long operationId) {
        return operationService.getOperation(operationId);
    }
}
