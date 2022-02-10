package com.zmerli.bank.exposition.dto.v1.operation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AmountConstraintValidator implements
        ConstraintValidator<AmountConstraint, Double> {

    @Override
    public void initialize(AmountConstraint amount) {
    }

    @Override
    public boolean isValid(Double amount,
                           ConstraintValidatorContext cxt) {
        return  null != amount &&  amount != 0d;
    }

}
