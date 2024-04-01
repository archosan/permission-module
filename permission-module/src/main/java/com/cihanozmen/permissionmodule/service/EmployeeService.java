package com.cihanozmen.permissionmodule.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.cihanozmen.permissionmodule.entity.Employee;
import com.cihanozmen.permissionmodule.exception.ResourceNotFoundException;
import com.cihanozmen.permissionmodule.mapper.EmployeeMapper;
import com.cihanozmen.permissionmodule.repository.EmployeeRepository;
import com.cihanozmen.permissionmodule.dto.EmployeeRequestDto;
import com.cihanozmen.permissionmodule.dto.EmployeeResponseDto;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private MessageSource messageSource;

    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeMapper.toEmployeeResponseDtoList(
                employeeRepository.findAll());
    }

    public EmployeeResponseDto getEmployeeById(int id) {

        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id, messageSource));

        return employeeMapper.toEmployeeResponseDto(emp);
    }

    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeDto) {

        Employee employee = new Employee();

        LocalDate startJobDate = employeeDto.getStartJobDate();

        employee.setTotalLeaveDays(calculateTotalLeaveDays(startJobDate));

        employeeMapper.updateEmployeeFromDto(employeeDto, employee);

        return employeeMapper.toEmployeeResponseDto(employeeRepository.save(employee));
    }

    private int calculateTotalLeaveDays(LocalDate startJobDate) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(startJobDate, currentDate);

        if (period.getYears() == 1 || period.getYears() == 0) {
            return 5;
        } else if (period.getYears() > 1 && period.getYears() <= 5) {
            return period.getYears() * 15;
        } else if (period.getYears() > 5 && period.getYears() <= 10) {
            return period.getYears() * 18;
        } else if (period.getYears() > 10) {
            return period.getYears() * 24;
        }
        return 0;
    }

}
