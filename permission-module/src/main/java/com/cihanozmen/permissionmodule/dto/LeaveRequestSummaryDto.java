package com.cihanozmen.permissionmodule.dto;

import java.time.LocalDate;

import com.cihanozmen.permissionmodule.enums.LeaveRequestStatus;

import lombok.Data;

@Data
public class LeaveRequestSummaryDto {
    private Integer id;
    private LocalDate leaveRequestStartDate;
    private LocalDate leaveRequestEndDate;
    private LeaveRequestStatus status;
}
