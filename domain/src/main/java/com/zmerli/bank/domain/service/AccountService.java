package com.zmerli.bank.domain.service;


import com.zmerli.bank.domain.entity.Account;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public interface AccountService {

    Account createOrUpdateAccount(Account account);

    Account getAccount(Integer accountNumber);

    @Transactional
    void updateAccountBalance(Account account, Double operationAmount);
}
