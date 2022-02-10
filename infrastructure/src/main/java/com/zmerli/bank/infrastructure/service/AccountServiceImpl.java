package com.zmerli.bank.infrastructure.service;

import com.zmerli.bank.domain.entity.Account;
import com.zmerli.bank.domain.service.AccountService;
import com.zmerli.bank.infrastructure.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createOrUpdateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(Integer accountNumber) {
        Optional<Account> optionalAccount = accountRepository.getAccountByAccountNumber(accountNumber);

        return optionalAccount.orElse(null);
    }

    @Override
    public void updateAccountBalance(Account account, Double operationAmount) {

        account.setAccountBalance(account.getAccountBalance() + operationAmount);
        accountRepository.save(account);
    }
}
