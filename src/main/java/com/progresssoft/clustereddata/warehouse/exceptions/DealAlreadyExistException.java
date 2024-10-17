package com.progresssoft.clustereddata.warehouse.exceptions;

public class DealAlreadyExistException extends RuntimeException {

    public DealAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
