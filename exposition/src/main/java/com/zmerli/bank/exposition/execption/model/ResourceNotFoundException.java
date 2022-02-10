package com.zmerli.bank.exposition.execption.model;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(Long id) {
        super(String.format("Resource not found %s.", id));
    }
}
