package com.progresssoft.clustereddata.warehouse.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationExceptionResponse {
    private List<String> validationErrors;
}
