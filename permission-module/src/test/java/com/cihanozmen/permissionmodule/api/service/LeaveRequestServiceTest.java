package com.cihanozmen.permissionmodule.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.cihanozmen.permissionmodule.dto.LeaveRequestCreateDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestCreateResponseDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestListDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestUpdateBodyDto;
import com.cihanozmen.permissionmodule.entity.Employee;
import com.cihanozmen.permissionmodule.entity.LeaveRequest;
import com.cihanozmen.permissionmodule.enums.EmployeeRole;
import com.cihanozmen.permissionmodule.exception.BadRequestException;
import com.cihanozmen.permissionmodule.exception.ResourceNotFoundException;
import com.cihanozmen.permissionmodule.mapper.LeaveRequestMapper;
import com.cihanozmen.permissionmodule.repository.EmployeeRepository;
import com.cihanozmen.permissionmodule.repository.LeaveRequestRepository;
import com.cihanozmen.permissionmodule.service.LeaveRequestService;

@ExtendWith(MockitoExtension.class)
public class LeaveRequestServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @Mock
    private LeaveRequestMapper leaveRequestMapper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private LeaveRequestService leaveRequestService;

    @Test
    public void createLeaveRequest_Successful() {
        int employeeId = 1;
        Employee requester = new Employee();
        LeaveRequestCreateDto createDto = new LeaveRequestCreateDto();
        LeaveRequest savedLeaveRequest = new LeaveRequest();
        LeaveRequestCreateResponseDto expectedResponse = new LeaveRequestCreateResponseDto();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(requester));
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(savedLeaveRequest);
        when(leaveRequestMapper.leaveRequestToLeaveRequestCreateResponseDto(savedLeaveRequest))
                .thenReturn(expectedResponse);

        LeaveRequestCreateResponseDto result = leaveRequestService.createLeaveRequest(employeeId, createDto);

        assertEquals(expectedResponse, result);
        verify(employeeRepository).findById(employeeId);
        verify(leaveRequestRepository).save(any(LeaveRequest.class));
        verify(leaveRequestMapper).leaveRequestToLeaveRequestCreateResponseDto(savedLeaveRequest);
    }

    @Test
    public void getLeaveRequests_Successful() {
        List<LeaveRequest> leaveRequests = Arrays.asList(new LeaveRequest(), new LeaveRequest());
        // data
        List<LeaveRequestListDto> expectedDtos = Arrays.asList(new LeaveRequestListDto(), new LeaveRequestListDto()); // values

        when(leaveRequestRepository.findAll()).thenReturn(leaveRequests);
        when(leaveRequestMapper.leaveRequestListToLeaveRequestListDto(leaveRequests)).thenReturn(expectedDtos);

        List<LeaveRequestListDto> result = leaveRequestService.getLeaveRequests();

        assertEquals(expectedDtos, result);
        verify(leaveRequestRepository).findAll();
        verify(leaveRequestMapper).leaveRequestListToLeaveRequestListDto(leaveRequests);
    }

    @Test
    public void changeStatusOfLeaveRequest_Successful() {
        int leaveRequestId = 1;
        LeaveRequestUpdateBodyDto updateBodyDto = new LeaveRequestUpdateBodyDto();
        Employee approver = new Employee();
        LeaveRequest leaveRequest = new LeaveRequest();
        LeaveRequestCreateResponseDto expectedResponse = new LeaveRequestCreateResponseDto();

        when(employeeRepository.findById(updateBodyDto.getApproverId())).thenReturn(Optional.of(approver));
        when(leaveRequestRepository.findById(leaveRequestId)).thenReturn(Optional.of(leaveRequest));
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(leaveRequest);
        when(leaveRequestMapper.leaveRequestToLeaveRequestCreateResponseDto(leaveRequest)).thenReturn(expectedResponse);

        LeaveRequestCreateResponseDto result = leaveRequestService.changeStatusOfLeaveRequest(leaveRequestId,
                updateBodyDto);

        assertEquals(expectedResponse, result);
        verify(employeeRepository).findById(updateBodyDto.getApproverId());
        verify(leaveRequestRepository).findById(leaveRequestId);
        verify(leaveRequestRepository).save(leaveRequest);
        verify(leaveRequestMapper).leaveRequestToLeaveRequestCreateResponseDto(leaveRequest);
    }

    @Test
    public void createLeaveRequest_ThrowsResourceNotFoundException_WhenEmployeeNotFound() {
        int employeeId = 1;
        LeaveRequestCreateDto createDto = new LeaveRequestCreateDto();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            leaveRequestService.createLeaveRequest(employeeId, createDto);
        });

        verify(employeeRepository).findById(employeeId);
        verifyNoInteractions(leaveRequestRepository);
    }

    @Test
    public void changeStatusOfLeaveRequest_ThrowsBadRequestException_WhenApproverIsNotAdmin() {
        int leaveRequestId = 1;
        LeaveRequestUpdateBodyDto updateBodyDto = new LeaveRequestUpdateBodyDto();
        updateBodyDto.setApproverId(2);
        Employee approver = new Employee();
        approver.setRole(EmployeeRole.USER);
        when(employeeRepository.findById(updateBodyDto.getApproverId())).thenReturn(Optional.of(approver));

        assertThrows(BadRequestException.class, () -> {
            leaveRequestService.changeStatusOfLeaveRequest(leaveRequestId, updateBodyDto);
        });

        verify(employeeRepository).findById(updateBodyDto.getApproverId());
        verifyNoInteractions(leaveRequestRepository);
    }

}
