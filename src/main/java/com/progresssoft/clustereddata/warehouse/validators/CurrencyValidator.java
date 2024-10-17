package com.progresssoft.clustereddata.warehouse.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Currency;

public class CurrencyValidator implements ConstraintValidator<ValidCurrency, Currency> {

    @Override
    public boolean isValid(Currency currency, ConstraintValidatorContext context) {
        if(currency == null) return false;

        try {
            Currency.getInstance(currency.getCurrencyCode());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}