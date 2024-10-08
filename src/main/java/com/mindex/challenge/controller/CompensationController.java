package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.exceptions.CompensationNotFoundException;
import com.mindex.challenge.service.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compensation")
public class CompensationController {

    private final CompensationService compensationService;

    @Autowired
    public CompensationController(CompensationService compensationService) {
        this.compensationService = compensationService;
    }

    @PostMapping
    public ResponseEntity<Compensation> create(@RequestBody Compensation compensation) {
        return new ResponseEntity<>(compensationService.create(compensation), HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Compensation> read(@PathVariable String employeeId) throws CompensationNotFoundException {
        Compensation compensation = compensationService.read(employeeId);
        return ResponseEntity.ok(compensation);
    }
}
