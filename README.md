Mindex Coding Challenge
# Overview

This project is a Spring Boot application that manages employee data and their compensation details. It includes functionality to handle employee information, reporting structures, and compensation details.
## Current State
### Features

    Employee Management:
        Endpoints:
            GET /employee/{id}: Retrieves employee details by employeeId.
            POST /employee: Creates a new employee entry.

    Reporting Structure:
        Endpoints:
            GET /reportingStructure/{employeeId}: Retrieves the reporting structure for a specified employee. This includes the employee and the total number of direct and indirect reports.

    Compensation Management:
        Endpoints:
            POST /compensation: Creates a new compensation entry for an employee.
            GET /compensation/{employeeId}: Retrieves compensation details by employeeId.

# Data Bootstrap

On application startup, the system loads initial employee data from employee_database.json. This data is used to populate the employee repository for testing.
## How to Test Using Postman

I made a really basic postman collection for simple testing

`https://api.postman.com/collections/7263283-f0d34ff2-157c-4d62-86ff-028c49bab3cc?access_key=PMAT-01J6CT8SNE1WMM02VKNZN4VCEP`

You can import this into postman if you want, and click around.

Future Improvements

    Error Handling:
        Implement more robust error handling for invalid inputs and service failures.
        Enhance the global exception handler to manage and format errors consistently.

    Integration Testing:
        Add integration tests to verify the interaction between different components of the system.
        The Unit tests are decent, but an integration test or two is definitely valuable in a real context.

    Data Validation:
        Implement additional validation for request bodies to ensure data integrity. I think lombok/spring has
        some more annotations for this.

    Documentation:
        Enhance API documentation to include detailed descriptions of endpoints, request/response examples, and error codes. 
        I think just getting some swagger annotations/definitions would make this way prettier/more digestable.
