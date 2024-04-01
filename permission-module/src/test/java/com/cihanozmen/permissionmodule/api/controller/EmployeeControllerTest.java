package com.cihanozmen.permissionmodule.api.controller;

import com.cihanozmen.permissionmodule.controller.EmployeeController;
import com.cihanozmen.permissionmodule.dto.EmployeeRequestDto;
import com.cihanozmen.permissionmodule.dto.EmployeeResponseDto;
import com.cihanozmen.permissionmodule.entity.Employee;
import com.cihanozmen.permissionmodule.mapper.EmployeeMapper;
import com.cihanozmen.permissionmodule.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployeeMapper employeeMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    private EmployeeRequestDto employeeRequestDto;
    private EmployeeResponseDto employeeResponseDto;
    private Employee employee;

    @BeforeEach
    public void setup() {
        employeeRequestDto = new EmployeeRequestDto();
        employeeResponseDto = new EmployeeResponseDto();
        employee = new Employee();
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        List<EmployeeResponseDto> employees = Arrays.asList(employeeResponseDto, employeeResponseDto);
        given(employeeService.getAllEmployees()).willReturn(employees);

        mockMvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        given(employeeService.getEmployeeById(eq(1))).willReturn(employeeResponseDto);

        mockMvc.perform(get("/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employeeResponseDto)));
    }

    @Test
    public void testCreateEmployee() throws Exception {

        given(employeeMapper.toEmployeeResponseDto(employee)).willReturn(employeeResponseDto);

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employee)));
    }

}
