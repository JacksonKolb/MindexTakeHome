package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<Optional<Compensation>> read(@PathVariable String employeeId) {
        return new ResponseEntity<>(compensationService.read(employeeId), HttpStatus.OK);
    }
}
