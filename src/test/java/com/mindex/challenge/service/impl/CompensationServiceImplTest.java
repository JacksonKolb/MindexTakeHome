package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationEmployeeIdUrl;
    private Compensation testCompensation;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationEmployeeIdUrl = "http://localhost:" + port + "/compensation/{employeeId}";

        // Setup test employee
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        // Create test employee in the database
        Employee createdEmployee = restTemplate.postForEntity("http://localhost:" + port + "/employee", testEmployee, Employee.class).getBody();
        assertNotNull(createdEmployee);

        // Setup test compensation
        testCompensation = new Compensation();
        testCompensation.setEmployee(createdEmployee);
        testCompensation.setSalary(BigDecimal.valueOf(100000));
        testCompensation.setEffectiveDate(LocalDate.now());
    }

    @Test
    public void testCreateCompensation() {
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        assertNotNull(createdCompensation);
        assertCompensationEquivalence(testCompensation, createdCompensation);
    }

    @Test
    public void testReadCompensation() {
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        Compensation readCompensation = restTemplate.getForEntity(compensationEmployeeIdUrl, Compensation.class, createdCompensation.getEmployee().getEmployeeId()).getBody();

        assertNotNull(readCompensation);
        assertCompensationEquivalence(createdCompensation, readCompensation);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
