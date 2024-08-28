package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.exceptions.CompensationNotFoundException;

public interface CompensationService {
    Compensation create(Compensation compensation);
    Compensation read(String employeeId) throws CompensationNotFoundException;
}
