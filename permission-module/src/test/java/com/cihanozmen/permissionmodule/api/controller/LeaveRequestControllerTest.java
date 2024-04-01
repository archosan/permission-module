package com.cihanozmen.permissionmodule.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import com.cihanozmen.permissionmodule.controller.LeaveRequestController;
import com.cihanozmen.permissionmodule.dto.LeaveRequestCreateDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestCreateResponseDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestListDto;
import com.cihanozmen.permissionmodule.dto.LeaveRequestUpdateBodyDto;
import com.cihanozmen.permissionmodule.service.LeaveRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LeaveRequestController.class)
@AutoConfigureMockMvc
public class LeaveRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeaveRequestService leaveRequestService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private LeaveRequestCreateDto createDto;
    private LeaveRequestUpdateBodyDto updateBodyDto;

    @BeforeEach
    public void setup() {
        createDto = new LeaveRequestCreateDto();
        updateBodyDto = new LeaveRequestUpdateBodyDto();
    }

    @Test
    public void testCreateLeaveRequest() throws Exception {
        LeaveRequestCreateResponseDto responseDto = new LeaveRequestCreateResponseDto();
        given(leaveRequestService.createLeaveRequest(eq(1), any(LeaveRequestCreateDto.class))).willReturn(responseDto);

        mockMvc.perform(post("/leave-requests/new/{employeeId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetLeaveRequests() throws Exception {
        List<LeaveRequestListDto> leaveRequests = Arrays.asList(new LeaveRequestListDto(), new LeaveRequestListDto());
        given(leaveRequestService.getLeaveRequests()).willReturn(leaveRequests);

        mockMvc.perform(get("/leave-requests")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(leaveRequests)));
    }

    @Test
    public void testUpdateLeaveRequest() throws Exception {
        LeaveRequestCreateResponseDto responseDto = new LeaveRequestCreateResponseDto();
        given(leaveRequestService.changeStatusOfLeaveRequest(eq(1), any(LeaveRequestUpdateBodyDto.class)))
                .willReturn(responseDto);

        mockMvc.perform(post("/leave-requests/change-status/{leaveRequestId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBodyDto)))
                .andExpect(status().isOk());
    }

}
