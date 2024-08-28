package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.exceptions.CompensationNotFoundException;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    private final CompensationRepository compensationRepository;

    @Autowired
    public CompensationServiceImpl(CompensationRepository compensationRepository) {
        this.compensationRepository = compensationRepository;
    }

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);
        return compensationRepository.save(compensation);
    }

    @Override
    public Compensation read(String employeeId) throws CompensationNotFoundException {
        LOG.debug("Retrieving compensation for employeeId [{}]", employeeId);
        return Optional.ofNullable(compensationRepository.findByEmployeeEmployeeId(employeeId))
                .orElseThrow(() -> new CompensationNotFoundException(employeeId));
    }
}
