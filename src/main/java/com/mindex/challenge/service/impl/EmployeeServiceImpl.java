package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Retrieving employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    @Override
    public ReportingStructure getReportingStructure(String employeeId) {
        LOG.debug("Calculating reporting structure for employee id [{}]", employeeId);

        Employee employee = this.read(employeeId);
        int numberOfReports = countReports(employee);

        return new ReportingStructure(employee, numberOfReports);
    }

    public int countReports(Employee employee) {
        int count = 0;

        if (employee.getDirectReports() != null) {
            LOG.debug("");
            Set<String> visited = new HashSet<>();
            count = countReportsRecursive(employee, visited);
        }

        return count;
    }

    private int countReportsRecursive(Employee employee, Set<String> visited) {
        LOG.debug("Entering countReportsRecursive for employee ID [{}]", employee.getEmployeeId());
        int count = 0;

        if (employee.getDirectReports() != null) {
            LOG.debug("Employee ID [{}] has {} direct reports.", employee.getEmployeeId(), employee.getDirectReports().size());
            for (Employee report : employee.getDirectReports()) {
                if (!visited.contains(report.getEmployeeId())) {
                    Employee fullEmployee = this.read(report.getEmployeeId());
                    count += 1 + countReportsRecursive(fullEmployee, visited);
                }
            }
        }

        return count;
    }
}
