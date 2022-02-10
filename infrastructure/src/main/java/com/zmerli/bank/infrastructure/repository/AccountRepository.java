package com.zmerli.bank.infrastructure.repository;


import com.zmerli.bank.domain.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> getAccountByAccountNumber(Integer accountNumber);

}
