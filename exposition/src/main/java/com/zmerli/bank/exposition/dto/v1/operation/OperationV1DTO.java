package com.zmerli.bank.exposition.dto.v1.operation;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationV1DTO {

    private Long id;
    private String operationType;
    private Double operationValue;
    private LocalDateTime date;
    private Double balance;

}
