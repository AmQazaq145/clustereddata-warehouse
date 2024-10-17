package com.progresssoft.clustereddata.warehouse.controllers.requests;

import com.progresssoft.clustereddata.warehouse.validators.ValidCurrency;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;

@Data
public class UpdateDealRequest {

    @NotNull(message = "uuid is mandatory")
    @NotEmpty(message = "uuid cannot be empty")
    private String uuid;

    @ValidCurrency(message = "Invalid or missing currency")
    private Currency toCurrency;

    @ValidCurrency(message = "Invalid or missing currency")
    private Currency fromCurrency;

    private BigDecimal amount;
}
