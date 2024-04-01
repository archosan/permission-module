package com.cihanozmen.permissionmodule.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LeaveRequestCreateDto {

    @NotNull(message = "{leaverequest.startdate.notblank}")
    private LocalDate leaveRequestStartDate;

    @NotNull(message = "{leaverequest.enddate.notblank}")
    private LocalDate leaveRequestEndDate;

    private String leaveRequestReason;
}
