package com.cihanozmen.permissionmodule.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.cihanozmen.permissionmodule.dto.EmployeeRequestDto;
import com.cihanozmen.permissionmodule.dto.EmployeeResponseDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestSummaryDto;
import com.cihanozmen.permissionmodule.entity.Employee;
import com.cihanozmen.permissionmodule.entity.LeaveRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeRequestDto toEmployeeRequestDto(Employee employee);

    Employee toEmployee(EmployeeRequestDto employeeRequestDto);

    Employee toEmployee(EmployeeResponseDto employeeResponseDto);

    EmployeeResponseDto toEmployeeResponseDto(Employee employee);

    @Mapping(target = "totalLeaveDays", ignore = true)
    void updateEmployeeFromDto(EmployeeRequestDto dto, @MappingTarget Employee employee);

    LeaveRequestSummaryDto toLeaveRequestSummaryDto(LeaveRequest leaveRequest);

    List<EmployeeResponseDto> toEmployeeResponseDtoList(List<Employee> employees);
}
