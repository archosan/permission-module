package com.cihanozmen.permissionmodule.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LeaveRequestListDto {

    private Integer id;
    private LocalDate leaveRequestStartDate;
    private LocalDate leaveRequestEndDate;
    private String status;
    private LocalDate leaveRequestCreatedAt;
    private EmployeeRequestDto requester;
    private String leaveRequestReason;
    private Integer givenLeaveDays;
    private Integer approver_id;

}
