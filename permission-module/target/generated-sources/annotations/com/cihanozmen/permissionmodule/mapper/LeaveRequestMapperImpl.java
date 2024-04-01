package com.cihanozmen.permissionmodule.mapper;

import com.cihanozmen.permissionmodule.dto.EmployeeRequestDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestCreateResponseDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestListDto;
import com.cihanozmen.permissionmodule.entity.Employee;
import com.cihanozmen.permissionmodule.entity.LeaveRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-01T13:11:17+0300",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240206-1609, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class LeaveRequestMapperImpl implements LeaveRequestMapper {

    @Override
    public LeaveRequestCreateResponseDto leaveRequestToLeaveRequestCreateResponseDto(LeaveRequest leaveRequest) {
        if ( leaveRequest == null ) {
            return null;
        }

        LeaveRequestCreateResponseDto leaveRequestCreateResponseDto = new LeaveRequestCreateResponseDto();

        leaveRequestCreateResponseDto.setRequesterId( leaveRequestRequesterId( leaveRequest ) );
        leaveRequestCreateResponseDto.setId( leaveRequest.getId() );
        if ( leaveRequest.getStatus() != null ) {
            leaveRequestCreateResponseDto.setStatus( leaveRequest.getStatus().name() );
        }

        return leaveRequestCreateResponseDto;
    }

    @Override
    public LeaveRequestListDto leaveRequestToLeaveRequestListDto(LeaveRequest leaveRequest) {
        if ( leaveRequest == null ) {
            return null;
        }

        LeaveRequestListDto leaveRequestListDto = new LeaveRequestListDto();

        leaveRequestListDto.setApprover_id( leaveRequest.getApprover_id() );
        leaveRequestListDto.setGivenLeaveDays( leaveRequest.getGivenLeaveDays() );
        leaveRequestListDto.setId( leaveRequest.getId() );
        leaveRequestListDto.setLeaveRequestCreatedAt( leaveRequest.getLeaveRequestCreatedAt() );
        leaveRequestListDto.setLeaveRequestEndDate( leaveRequest.getLeaveRequestEndDate() );
        leaveRequestListDto.setLeaveRequestReason( leaveRequest.getLeaveRequestReason() );
        leaveRequestListDto.setLeaveRequestStartDate( leaveRequest.getLeaveRequestStartDate() );
        leaveRequestListDto.setRequester( employeeToEmployeeRequestDto( leaveRequest.getRequester() ) );
        if ( leaveRequest.getStatus() != null ) {
            leaveRequestListDto.setStatus( leaveRequest.getStatus().name() );
        }

        return leaveRequestListDto;
    }

    @Override
    public List<LeaveRequestListDto> leaveRequestListToLeaveRequestListDto(List<LeaveRequest> leaveRequestList) {
        if ( leaveRequestList == null ) {
            return null;
        }

        List<LeaveRequestListDto> list = new ArrayList<LeaveRequestListDto>( leaveRequestList.size() );
        for ( LeaveRequest leaveRequest : leaveRequestList ) {
            list.add( leaveRequestToLeaveRequestListDto( leaveRequest ) );
        }

        return list;
    }

    private Integer leaveRequestRequesterId(LeaveRequest leaveRequest) {
        if ( leaveRequest == null ) {
            return null;
        }
        Employee requester = leaveRequest.getRequester();
        if ( requester == null ) {
            return null;
        }
        Integer id = requester.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected EmployeeRequestDto employeeToEmployeeRequestDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();

        employeeRequestDto.setEmail( employee.getEmail() );
        employeeRequestDto.setName( employee.getName() );
        if ( employee.getRole() != null ) {
            employeeRequestDto.setRole( employee.getRole().name() );
        }
        employeeRequestDto.setStartJobDate( employee.getStartJobDate() );
        employeeRequestDto.setSurname( employee.getSurname() );

        return employeeRequestDto;
    }
}
