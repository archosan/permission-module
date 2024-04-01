package com.cihanozmen.permissionmodule.mapper;

import com.cihanozmen.permissionmodule.dto.EmployeeRequestDto;
import com.cihanozmen.permissionmodule.dto.EmployeeResponseDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestSummaryDto;
import com.cihanozmen.permissionmodule.entity.Employee;
import com.cihanozmen.permissionmodule.entity.Employee.EmployeeBuilder;
import com.cihanozmen.permissionmodule.entity.LeaveRequest;
import com.cihanozmen.permissionmodule.entity.LeaveRequest.LeaveRequestBuilder;
import com.cihanozmen.permissionmodule.enums.EmployeeRole;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-01T13:11:17+0300",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240206-1609, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeRequestDto toEmployeeRequestDto(Employee employee) {
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

    @Override
    public Employee toEmployee(EmployeeRequestDto employeeRequestDto) {
        if ( employeeRequestDto == null ) {
            return null;
        }

        EmployeeBuilder employee = Employee.builder();

        employee.email( employeeRequestDto.getEmail() );
        employee.name( employeeRequestDto.getName() );
        if ( employeeRequestDto.getRole() != null ) {
            employee.role( Enum.valueOf( EmployeeRole.class, employeeRequestDto.getRole() ) );
        }
        employee.startJobDate( employeeRequestDto.getStartJobDate() );
        employee.surname( employeeRequestDto.getSurname() );

        return employee.build();
    }

    @Override
    public Employee toEmployee(EmployeeResponseDto employeeResponseDto) {
        if ( employeeResponseDto == null ) {
            return null;
        }

        EmployeeBuilder employee = Employee.builder();

        employee.email( employeeResponseDto.getEmail() );
        employee.id( employeeResponseDto.getId() );
        employee.leaveRequests( leaveRequestSummaryDtoSetToLeaveRequestSet( employeeResponseDto.getLeaveRequests() ) );
        employee.name( employeeResponseDto.getName() );
        employee.remainingLeaveDays( employeeResponseDto.getRemainingLeaveDays() );
        if ( employeeResponseDto.getRole() != null ) {
            employee.role( Enum.valueOf( EmployeeRole.class, employeeResponseDto.getRole() ) );
        }
        if ( employeeResponseDto.getStartJobDate() != null ) {
            employee.startJobDate( LocalDate.parse( employeeResponseDto.getStartJobDate() ) );
        }
        employee.surname( employeeResponseDto.getSurname() );
        employee.totalLeaveDays( employeeResponseDto.getTotalLeaveDays() );
        employee.usedLeaveDays( employeeResponseDto.getUsedLeaveDays() );

        return employee.build();
    }

    @Override
    public EmployeeResponseDto toEmployeeResponseDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();

        employeeResponseDto.setEmail( employee.getEmail() );
        employeeResponseDto.setId( employee.getId() );
        employeeResponseDto.setLeaveRequests( leaveRequestSetToLeaveRequestSummaryDtoSet( employee.getLeaveRequests() ) );
        employeeResponseDto.setName( employee.getName() );
        employeeResponseDto.setRemainingLeaveDays( employee.getRemainingLeaveDays() );
        if ( employee.getRole() != null ) {
            employeeResponseDto.setRole( employee.getRole().name() );
        }
        if ( employee.getStartJobDate() != null ) {
            employeeResponseDto.setStartJobDate( DateTimeFormatter.ISO_LOCAL_DATE.format( employee.getStartJobDate() ) );
        }
        employeeResponseDto.setSurname( employee.getSurname() );
        employeeResponseDto.setTotalLeaveDays( employee.getTotalLeaveDays() );
        employeeResponseDto.setUsedLeaveDays( employee.getUsedLeaveDays() );

        return employeeResponseDto;
    }

    @Override
    public void updateEmployeeFromDto(EmployeeRequestDto dto, Employee employee) {
        if ( dto == null ) {
            return;
        }

        employee.setEmail( dto.getEmail() );
        employee.setName( dto.getName() );
        if ( dto.getRole() != null ) {
            employee.setRole( Enum.valueOf( EmployeeRole.class, dto.getRole() ) );
        }
        else {
            employee.setRole( null );
        }
        employee.setStartJobDate( dto.getStartJobDate() );
        employee.setSurname( dto.getSurname() );
    }

    @Override
    public LeaveRequestSummaryDto toLeaveRequestSummaryDto(LeaveRequest leaveRequest) {
        if ( leaveRequest == null ) {
            return null;
        }

        LeaveRequestSummaryDto leaveRequestSummaryDto = new LeaveRequestSummaryDto();

        leaveRequestSummaryDto.setId( leaveRequest.getId() );
        leaveRequestSummaryDto.setLeaveRequestEndDate( leaveRequest.getLeaveRequestEndDate() );
        leaveRequestSummaryDto.setLeaveRequestStartDate( leaveRequest.getLeaveRequestStartDate() );
        leaveRequestSummaryDto.setStatus( leaveRequest.getStatus() );

        return leaveRequestSummaryDto;
    }

    @Override
    public List<EmployeeResponseDto> toEmployeeResponseDtoList(List<Employee> employees) {
        if ( employees == null ) {
            return null;
        }

        List<EmployeeResponseDto> list = new ArrayList<EmployeeResponseDto>( employees.size() );
        for ( Employee employee : employees ) {
            list.add( toEmployeeResponseDto( employee ) );
        }

        return list;
    }

    protected LeaveRequest leaveRequestSummaryDtoToLeaveRequest(LeaveRequestSummaryDto leaveRequestSummaryDto) {
        if ( leaveRequestSummaryDto == null ) {
            return null;
        }

        LeaveRequestBuilder leaveRequest = LeaveRequest.builder();

        leaveRequest.id( leaveRequestSummaryDto.getId() );
        leaveRequest.leaveRequestEndDate( leaveRequestSummaryDto.getLeaveRequestEndDate() );
        leaveRequest.leaveRequestStartDate( leaveRequestSummaryDto.getLeaveRequestStartDate() );
        leaveRequest.status( leaveRequestSummaryDto.getStatus() );

        return leaveRequest.build();
    }

    protected Set<LeaveRequest> leaveRequestSummaryDtoSetToLeaveRequestSet(Set<LeaveRequestSummaryDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<LeaveRequest> set1 = new HashSet<LeaveRequest>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( LeaveRequestSummaryDto leaveRequestSummaryDto : set ) {
            set1.add( leaveRequestSummaryDtoToLeaveRequest( leaveRequestSummaryDto ) );
        }

        return set1;
    }

    protected Set<LeaveRequestSummaryDto> leaveRequestSetToLeaveRequestSummaryDtoSet(Set<LeaveRequest> set) {
        if ( set == null ) {
            return null;
        }

        Set<LeaveRequestSummaryDto> set1 = new HashSet<LeaveRequestSummaryDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( LeaveRequest leaveRequest : set ) {
            set1.add( toLeaveRequestSummaryDto( leaveRequest ) );
        }

        return set1;
    }
}
