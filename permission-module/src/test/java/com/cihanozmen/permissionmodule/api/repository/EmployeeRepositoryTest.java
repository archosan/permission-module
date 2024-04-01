package com.cihanozmen.permissionmodule.api.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cihanozmen.permissionmodule.entity.Employee;
import com.cihanozmen.permissionmodule.repository.EmployeeRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void saveAndReturnEmployee() {

        Employee employee = Employee.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@gmail.com")
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void findById() {

        Employee employee = Employee.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@gmail.com")
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        Employee foundEmployee = employeeRepository.findById(savedEmployee.getId()).orElse(null);

        Assertions.assertThat(foundEmployee).isNotNull();
        Assertions.assertThat(foundEmployee.getId()).isEqualTo(savedEmployee.getId());
    }

    @Test
    public void delete() {

        Employee employee = Employee.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@gmail.com")
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        employeeRepository.delete(savedEmployee);

        Employee foundEmployee = employeeRepository.findById(savedEmployee.getId()).orElse(null);

        Assertions.assertThat(foundEmployee).isNull();
    }

    @Test
    public void update() {

        Employee employee = Employee.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@gmail.com")
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        savedEmployee.setName("Jane");

        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        Assertions.assertThat(updatedEmployee).isNotNull();
        Assertions.assertThat(updatedEmployee.getId()).isEqualTo(savedEmployee.getId());
        Assertions.assertThat(updatedEmployee.getName()).isEqualTo("Jane");

    }

    @Test
    public void findAll() {

        Employee employee1 = Employee.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@gmail.com")
                .build();

        Employee employee2 = Employee.builder()
                .name("Jane")
                .surname("Doe")
                .email("jane.doe@gmail.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        Assertions.assertThat(employeeRepository.findAll()).hasSize(2);
    }

}
