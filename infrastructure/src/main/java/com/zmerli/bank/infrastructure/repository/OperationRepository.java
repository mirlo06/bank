package com.zmerli.bank.infrastructure.repository;


import com.zmerli.bank.domain.entity.Account;
import com.zmerli.bank.domain.entity.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OperationRepository extends CrudRepository<Operation, Long> {

    List<Operation> findAllByAccount(Account account, Pageable page);

}
