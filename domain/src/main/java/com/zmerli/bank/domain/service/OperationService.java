package com.zmerli.bank.domain.service;


import com.zmerli.bank.domain.entity.Account;
import com.zmerli.bank.domain.entity.Operation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OperationService {

    Operation saveOperation(Account account, Double operationValue);

    List<Operation> getAllOperationByAccount(Account account, int page, int size);

    Operation getOperation(Long operationId);
}
