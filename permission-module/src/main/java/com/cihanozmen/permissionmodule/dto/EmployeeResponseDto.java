package com.cihanozmen.permissionmodule.dto;

import java.util.Set;

import lombok.Data;

@Data
public class EmployeeResponseDto {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String startJobDate;
    private Integer usedLeaveDays;
    private Integer remainingLeaveDays;
    private Integer totalLeaveDays;
    private String role;
    private Set<LeaveRequestSummaryDto> leaveRequests;

}
