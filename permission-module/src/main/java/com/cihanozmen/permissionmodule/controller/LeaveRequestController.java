package com.cihanozmen.permissionmodule.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cihanozmen.permissionmodule.dto.LeaveRequestCreateDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestCreateResponseDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestListDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestUpdateBodyDto;
import com.cihanozmen.permissionmodule.service.LeaveRequestService;

@RestController
public class LeaveRequestController {
    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping("/leave-requests/new/{employeeId}")
    public ResponseEntity<LeaveRequestCreateResponseDto> createLeaveRequest(@PathVariable int employeeId,
            @RequestBody LeaveRequestCreateDto leaveRequestCreateDto) {
        LeaveRequestCreateResponseDto created = leaveRequestService.createLeaveRequest(employeeId,
                leaveRequestCreateDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/leave-requests")
    public ResponseEntity<List<LeaveRequestListDto>> getLeaveRequests() {
        List<LeaveRequestListDto> leaveRequests = leaveRequestService.getLeaveRequests();
        return ResponseEntity.ok(leaveRequests);
    }

    @PostMapping("/leave-requests/change-status/{leaveRequestId}")
    public LeaveRequestCreateResponseDto updateLeaveRequest(@PathVariable int leaveRequestId,
            @Valid @RequestBody LeaveRequestUpdateBodyDto leaveRequestUpdataDto) {
        return leaveRequestService.changeStatusOfLeaveRequest(leaveRequestId, leaveRequestUpdataDto);
    }

}
