package com.cihanozmen.permissionmodule.dto;

import lombok.Data;

@Data
public class LeaveRequestCreateResponseDto {

    private Integer id;
    private String startDate;
    private String endDate;
    private String status;
    private Integer requesterId;
}
