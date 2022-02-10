package com.zmerli.bank.infrastructure.service;

import com.zmerli.bank.domain.entity.Account;
import com.zmerli.bank.domain.entity.Operation;
import com.zmerli.bank.domain.service.OperationService;
import com.zmerli.bank.infrastructure.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.DoubleFunction;

@Repository
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationRepository operationRepository;

    private final static DoubleFunction<String> evaluateOperationType = operationValue -> operationValue > 0 ? "DEPOSIT" : "WITHDRAWAL";

    @Override
    public Operation saveOperation(Account account, Double operationValue) {
        Operation operation = new Operation(evaluateOperationType.apply(operationValue), operationValue, account);

        return operationRepository.save(operation);
    }

    @Override
    public List<Operation> getAllOperationByAccount(Account account, int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
        return operationRepository.findAllByAccount(account, pageRequest);
    }

    @Override
    public Operation getOperation(Long operationId) {
        return operationRepository.findById(operationId).orElse(null);
    }
}
