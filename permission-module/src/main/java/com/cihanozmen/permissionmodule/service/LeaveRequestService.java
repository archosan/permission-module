package com.cihanozmen.permissionmodule.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.cihanozmen.permissionmodule.dto.LeaveRequestCreateDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestCreateResponseDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestListDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestUpdateBodyDto;
import com.cihanozmen.permissionmodule.entity.Employee;
import com.cihanozmen.permissionmodule.entity.LeaveRequest;
import com.cihanozmen.permissionmodule.enums.EmployeeRole;
import com.cihanozmen.permissionmodule.enums.LeaveRequestStatus;
import com.cihanozmen.permissionmodule.exception.BadRequestException;
import com.cihanozmen.permissionmodule.exception.ResourceNotFoundException;
import com.cihanozmen.permissionmodule.mapper.LeaveRequestMapper;
import com.cihanozmen.permissionmodule.repository.EmployeeRepository;
import com.cihanozmen.permissionmodule.repository.LeaveRequestRepository;
import com.cihanozmen.permissionmodule.utils.DateUtils;

@Service
public class LeaveRequestService {

        @Autowired
        private LeaveRequestRepository leaveRequestRepository;

        @Autowired
        private LeaveRequestMapper leaveRequestMapper;

        @Autowired
        private EmployeeRepository employeeRepository;

        @Autowired
        private DateUtils dateUtils;

        @Autowired
        private MessageSource messageSource;

        public LeaveRequestCreateResponseDto createLeaveRequest(int employeeId,
                        LeaveRequestCreateDto leaveRequestCreateDto) {

                Employee requester = employeeRepository.findById(employeeId)
                                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id",
                                                employeeId, messageSource));

                LocalDate leaveRequestStartDate = leaveRequestCreateDto.getLeaveRequestStartDate();
                LocalDate leaveRequestEndDate = leaveRequestCreateDto.getLeaveRequestEndDate();

                int countOfRequestedLeaveDays = Period.between(leaveRequestStartDate, leaveRequestEndDate).getDays();

                int dayOff = dateUtils.calculateWeekendAndHolidayDays(leaveRequestStartDate, leaveRequestEndDate);

                countOfRequestedLeaveDays -= dayOff;

                if (countOfRequestedLeaveDays <= requester.getRemainingLeaveDays()) {
                        throw new BadRequestException("{employee.notenoughleavedays}");
                }

                LeaveRequest leaveRequest = new LeaveRequest();
                leaveRequest.setRequester(requester);
                leaveRequest.setLeaveRequestStartDate(leaveRequestStartDate);
                leaveRequest.setLeaveRequestEndDate(leaveRequestEndDate);
                leaveRequest.setLeaveRequestReason(leaveRequestCreateDto.getLeaveRequestReason());
                leaveRequest.setGivenLeaveDays(countOfRequestedLeaveDays);
                leaveRequest.setLeaveRequestCreatedAt(LocalDate.now());
                leaveRequest.setStatus(LeaveRequestStatus.PENDING);

                return leaveRequestMapper
                                .leaveRequestToLeaveRequestCreateResponseDto(leaveRequestRepository.save(leaveRequest));

        }

        public List<LeaveRequestListDto> getLeaveRequests() {
                return leaveRequestMapper.leaveRequestListToLeaveRequestListDto(leaveRequestRepository.findAll());
        }

        public LeaveRequestCreateResponseDto changeStatusOfLeaveRequest(int leaveRequestId,
                        LeaveRequestUpdateBodyDto leaveRequestUpdateBodyDto) {
                Employee approver = employeeRepository.findById(leaveRequestUpdateBodyDto.getApproverId())
                                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id",
                                                leaveRequestUpdateBodyDto.getApproverId(), messageSource));

                if (!approver.getRole().equals(EmployeeRole.ADMIN)) {
                        throw new BadRequestException("{employee.notadmin}");
                }

                LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                                .orElseThrow(() -> new ResourceNotFoundException("LeaveRequest", "id",
                                                leaveRequestId, messageSource));

                Employee requester = employeeRepository.findById(leaveRequest.getRequester().getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id",
                                                leaveRequest.getRequester().getId(), messageSource));

                if (leaveRequestUpdateBodyDto.getStatus().equals(LeaveRequestStatus.APPROVED)) {
                        requester.setUsedLeaveDays(leaveRequest.getGivenLeaveDays());
                        requester.setRemainingLeaveDays(
                                        requester.getTotalLeaveDays() - leaveRequest.getGivenLeaveDays());
                        employeeRepository.save(requester);
                }

                leaveRequest.setApprover_id(approver.getId());
                leaveRequest.setStatus(leaveRequestUpdateBodyDto.getStatus());

                return leaveRequestMapper
                                .leaveRequestToLeaveRequestCreateResponseDto(leaveRequestRepository.save(leaveRequest));
        }

}
