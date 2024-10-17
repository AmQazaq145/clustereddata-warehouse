package com.progresssoft.clustereddata.warehouse.controllers.requests;

import com.progresssoft.clustereddata.warehouse.validators.ValidCurrency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;

@Data
public class CreateDealRequest {

    @NotNull(message = "to currency is mandatory")
    @ValidCurrency(message = "Invalid or missing currency")
    private Currency toCurrency;

    @NotNull(message = "from currency is mandatory")
    @ValidCurrency(message = "Invalid or missing currency")
    private Currency fromCurrency;

    @NotNull(message = "amount is mandatory")
    @Min(value = 0, message = "amount cannot be negative")
    private BigDecimal amount;

}
