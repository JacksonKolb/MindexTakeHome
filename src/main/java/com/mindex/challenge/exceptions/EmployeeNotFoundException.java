package com.mindex.challenge.exceptions;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String employeeId) {
        super("Employee not found with id: " + employeeId);
    }
}
