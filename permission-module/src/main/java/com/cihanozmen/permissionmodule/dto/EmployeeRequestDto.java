package com.cihanozmen.permissionmodule.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {

    @NotNull(message = "{employee.name.notblank}")
    @Size(min = 4, message = "{employee.name.size}")
    @Size(max = 20, message = "{employee.name.size}")
    private String name;
    @NotNull(message = "{employee.surname.notblank}")
    @Size(min = 4, message = "{employee.surname.size}")
    @Size(max = 20, message = "{employee.surname.size}")
    private String surname;

    @NotNull(message = "{employee.email.notblank}")
    @Email(message = "{employee.email.invalid}")
    private String email;

    @NotNull(message = "{employee.startJobDate.notblank}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startJobDate;

    @NotNull(message = "{employee.role.notblank}")
    private String role;

}
