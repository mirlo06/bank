package com.zmerli.bank.exposition.dto.v1.operation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class OperationRequestV1 {

    @NotNull
    private Integer accountNumber;
    @AmountConstraint
    private Double operationAmount;
}
