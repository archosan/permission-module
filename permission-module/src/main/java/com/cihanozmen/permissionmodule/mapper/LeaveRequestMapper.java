package com.cihanozmen.permissionmodule.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cihanozmen.permissionmodule.dto.LeaveRequestCreateResponseDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestListDto;
import com.cihanozmen.permissionmodule.entity.LeaveRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface LeaveRequestMapper {

    LeaveRequestMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(LeaveRequestMapper.class);

    @Mapping(source = "requester.id", target = "requesterId")
    LeaveRequestCreateResponseDto leaveRequestToLeaveRequestCreateResponseDto(LeaveRequest leaveRequest);

    LeaveRequestListDto leaveRequestToLeaveRequestListDto(LeaveRequest leaveRequest);

    List<LeaveRequestListDto> leaveRequestListToLeaveRequestListDto(List<LeaveRequest> leaveRequestList);

}
