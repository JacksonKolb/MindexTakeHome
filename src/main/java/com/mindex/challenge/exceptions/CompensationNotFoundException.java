package com.mindex.challenge.exceptions;

public class CompensationNotFoundException extends Exception {
    public CompensationNotFoundException(String employeeId) {
        super("Compensation not found for employeeId: " + employeeId);
    }
}
