package com.progresssoft.clustereddata.warehouse.models;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;


@Data
public class Deal {

    private String uuid;

    private Currency toCurrency;

    private Currency fromCurrency;

    private BigDecimal amount;

}
