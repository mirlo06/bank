package com.zmerli.bank.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String operationType;
    private Double operationValue;
    private LocalDateTime date;
    private Double balance;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Account account;

    public Operation(String operationType, Double operationValue, Account account) {
        this.operationType = operationType;
        this.operationValue = operationValue;
        this.account = account;
        this.balance = account.getAccountBalance();
        this.date = LocalDateTime.now();
    }

    public Operation() {
    }

}
