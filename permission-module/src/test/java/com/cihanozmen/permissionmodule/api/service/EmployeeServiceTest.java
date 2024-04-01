package com.cihanozmen.permissionmodule.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.cihanozmen.permissionmodule.dto.EmployeeRequestDto;
import com.cihanozmen.permissionmodule.dto.EmployeeResponseDto;
import com.cihanozmen.permissionmodule.entity.Employee;
import com.cihanozmen.permissionmodule.exception.ResourceNotFoundException;
import com.cihanozmen.permissionmodule.mapper.EmployeeMapper;
import com.cihanozmen.permissionmodule.repository.EmployeeRepository;
import com.cihanozmen.permissionmodule.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private MessageSource messageSource;

    @Test
    public void getAllEmployees_ShouldReturnAllEmployees() {
        // Verileri hazırla
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        List<Employee> employeeList = Arrays.asList(employee1, employee2);

        EmployeeResponseDto dto1 = new EmployeeResponseDto();
        EmployeeResponseDto dto2 = new EmployeeResponseDto();
        List<EmployeeResponseDto> dtoList = Arrays.asList(dto1, dto2);

        when(employeeRepository.findAll()).thenReturn(employeeList);
        when(employeeMapper.toEmployeeResponseDtoList(employeeList)).thenReturn(dtoList);

        List<EmployeeResponseDto> result = employeeService.getAllEmployees();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dtoList, result);

        verify(employeeRepository).findAll();
        verify(employeeMapper).toEmployeeResponseDtoList(employeeList);
    }

    @Test
    public void createEmployee_ShouldCreateAndReturnNewEmployee() {
        EmployeeRequestDto requestDto = new EmployeeRequestDto();
        Employee savedEmployee = new Employee();
        EmployeeResponseDto expectedDto = new EmployeeResponseDto();

        when(employeeMapper.toEmployeeResponseDto(any(Employee.class))).thenReturn(expectedDto);
        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        EmployeeResponseDto resultDto = employeeService.createEmployee(requestDto);

        assertEquals(expectedDto, resultDto);
        verify(employeeMapper).updateEmployeeFromDto(eq(requestDto), any(Employee.class));
        verify(employeeRepository).save(any(Employee.class));
        verify(employeeMapper).toEmployeeResponseDto(any(Employee.class));
    }

    @Test
    public void getEmployeeById_ShouldReturnEmployee_WhenEmployeeExists() {
        int employeeId = 1;
        Employee employee = new Employee();
        EmployeeResponseDto expectedDto = new EmployeeResponseDto();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeMapper.toEmployeeResponseDto(employee)).thenReturn(expectedDto);

        EmployeeResponseDto resultDto = employeeService.getEmployeeById(employeeId);

        assertEquals(expectedDto, resultDto);
        verify(employeeRepository).findById(employeeId);
        verify(employeeMapper).toEmployeeResponseDto(employee);
    }

    @Test
    public void getEmployeeById_ShouldThrowResourceNotFoundException_WhenEmployeeDoesNotExist() {
        int employeeId = 1;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Error message");

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(employeeId);
        });
    }

}
