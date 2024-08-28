package com.mindex.challenge.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Compensation {
    private Employee employee;
    private BigDecimal salary;
    private LocalDate effectiveDate;
}
