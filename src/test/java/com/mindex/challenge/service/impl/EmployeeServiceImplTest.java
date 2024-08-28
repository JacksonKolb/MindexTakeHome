package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.exceptions.EmployeeNotFoundException;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceImplTest {

    private String employeeIdUrl;
    private Employee testEmployee;
    private Employee createdEmployee;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        String employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";

        testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
    }

    @Test
    public void testCreateEmployee() {
        assertNotNull(createdEmployee.getEmployeeId());
        assertEmployeeEquivalence(testEmployee, createdEmployee);
    }

    @Test
    public void testReadEmployee() {
        Employee readEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();

        assertNotNull(readEmployee);
        assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
        assertEmployeeEquivalence(createdEmployee, readEmployee);
    }

    @Test
    public void testUpdateEmployee() {
        createdEmployee.setPosition("Development Manager");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee updatedEmployee =
                restTemplate.exchange(employeeIdUrl,
                        HttpMethod.PUT,
                        new HttpEntity<>(createdEmployee, headers),
                        Employee.class,
                        createdEmployee.getEmployeeId()).getBody();

        assertNotNull(updatedEmployee);
        assertEmployeeEquivalence(createdEmployee, updatedEmployee);
    }

    @Test
    public void testCountReportsNoReports() throws EmployeeNotFoundException {
        String employeeIdWithNoReports = "62c1084e-6e34-4630-93fd-9153afb65309";

        Employee employee = employeeService.read(employeeIdWithNoReports);
        int reportCount = employeeService.countReports(employee);

        assertEquals(0, reportCount);
    }

    @Test
    public void testCountReportsOneLevel() throws EmployeeNotFoundException {
        String employeeIdWithOneLevel = "03aa1462-ffa9-4978-901b-7c001562cf6f";

        Employee employee = employeeService.read(employeeIdWithOneLevel);
        int reportCount = employeeService.countReports(employee);

        assertEquals(2, reportCount);
    }

    @Test
    public void testCountReportsMultipleLevels() throws EmployeeNotFoundException {
        String employeeIdWithMultipleLevels = "16a596ae-edd3-4847-99fe-c4518e82c86f";

        Employee employee = employeeService.read(employeeIdWithMultipleLevels);
        int reportCount = employeeService.countReports(employee);

        assertEquals(4, reportCount);
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
