package com.cihanozmen.permissionmodule.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.cihanozmen.permissionmodule.enums.LeaveRequestStatus;

import lombok.Data;

@Data
public class LeaveRequestUpdateBodyDto {

    @NotNull(message = "{leaverequest.approver.notblank}")
    @Min(value = 1, message = "{leaverequest.approver.invalid}")
    private Integer approverId;

    @NotNull(message = "{leaverequest.status.notblank}")
    private LeaveRequestStatus status;
}
