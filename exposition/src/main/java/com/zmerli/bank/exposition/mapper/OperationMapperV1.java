package com.zmerli.bank.exposition.mapper;

import com.zmerli.bank.domain.entity.Operation;
import com.zmerli.bank.exposition.dto.v1.operation.OperationV1DTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OperationMapperV1 {

    OperationV1DTO toOperationDTOV1(Operation operation);

    List<OperationV1DTO> toListOperationDTOV1(List<Operation> operations);

}

