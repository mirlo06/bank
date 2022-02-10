package com.zmerli.bank.exposition.execption.model;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(Integer accountNumber) {
        super(String.format("Account number %s has not been found.", accountNumber));
    }
}
