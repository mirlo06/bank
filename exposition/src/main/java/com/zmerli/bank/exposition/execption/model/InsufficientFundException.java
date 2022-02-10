package com.zmerli.bank.exposition.execption.model;

public class InsufficientFundException extends Exception {
    public InsufficientFundException(Integer accountNumber) {
        super(String.format("operation refused, insufficient fund for %s.", accountNumber));
    }
}
