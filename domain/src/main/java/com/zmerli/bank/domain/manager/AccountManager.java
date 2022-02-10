package com.zmerli.bank.domain.manager;

import com.zmerli.bank.domain.entity.Account;
import com.zmerli.bank.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class AccountManager {

    @Autowired
    private AccountService accountService;

    public Account createOrUpdateAccount(Account account) {
        return accountService.createOrUpdateAccount(account);
    }

    public Account getAccount(Integer accountNumber) {
        return accountService.getAccount(accountNumber);
    }

    @Transactional
    public void updateAccountBalance(Account account, Double operationAmount) {
        accountService.updateAccountBalance(account, operationAmount);
    }
}
