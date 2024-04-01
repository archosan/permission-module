package com.cihanozmen.permissionmodule.api.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cihanozmen.permissionmodule.entity.LeaveRequest;
import com.cihanozmen.permissionmodule.repository.LeaveRequestRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LeaveRequestRepositoryTest {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Test
    public void saveAndReturnLeaveRequest() {

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .leaveRequestStartDate(LocalDate.of(2021, 1, 1))
                .leaveRequestEndDate(LocalDate.of(2021, 1, 5))
                .leaveRequestReason("Sick")
                .build();

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);

        Assertions.assertThat(savedLeaveRequest).isNotNull();
        Assertions.assertThat(savedLeaveRequest.getId()).isGreaterThan(0);

    }

    @Test
    public void findById() {
        LeaveRequest leaveRequest = LeaveRequest.builder()
                .leaveRequestStartDate(LocalDate.of(2021, 1, 1))
                .leaveRequestEndDate(LocalDate.of(2021, 1, 5))
                .leaveRequestReason("Sick")
                .build();

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);

        LeaveRequest foundLeaveRequest = leaveRequestRepository.findById(savedLeaveRequest.getId()).orElse(null);

        Assertions.assertThat(foundLeaveRequest).isNotNull();
        Assertions.assertThat(foundLeaveRequest.getId()).isEqualTo(savedLeaveRequest.getId());
    }

    @Test
    public void delete() {
        LeaveRequest leaveRequest = LeaveRequest.builder()
                .leaveRequestStartDate(LocalDate.of(2021, 1, 1))
                .leaveRequestEndDate(LocalDate.of(2021, 1, 5))
                .leaveRequestReason("Sick")
                .build();

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);

        leaveRequestRepository.delete(savedLeaveRequest);

        LeaveRequest foundLeaveRequest = leaveRequestRepository.findById(savedLeaveRequest.getId()).orElse(null);

        Assertions.assertThat(foundLeaveRequest).isNull();
    }

    @Test
    public void update() {
        LeaveRequest leaveRequest = LeaveRequest.builder()
                .leaveRequestStartDate(LocalDate.of(2021, 1, 1))
                .leaveRequestEndDate(LocalDate.of(2021, 1, 5))
                .leaveRequestReason("Sick")
                .build();

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);

        savedLeaveRequest.setLeaveRequestReason("Vacation");

        LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(savedLeaveRequest);

        Assertions.assertThat(updatedLeaveRequest).isNotNull();
        Assertions.assertThat(updatedLeaveRequest.getId()).isEqualTo(savedLeaveRequest.getId());
        Assertions.assertThat(updatedLeaveRequest.getLeaveRequestReason()).isEqualTo("Vacation");
    }

    @Test
    public void findAll() {
        LeaveRequest leaveRequest1 = LeaveRequest.builder()
                .leaveRequestStartDate(LocalDate.of(2021, 1, 1))
                .leaveRequestEndDate(LocalDate.of(2021, 1, 5))
                .leaveRequestReason("Sick")
                .build();

        LeaveRequest leaveRequest2 = LeaveRequest.builder()
                .leaveRequestStartDate(LocalDate.of(2021, 1, 1))
                .leaveRequestEndDate(LocalDate.of(2021, 1, 5))
                .leaveRequestReason("Vacation")
                .build();

        leaveRequestRepository.save(leaveRequest1);
        leaveRequestRepository.save(leaveRequest2);

        Assertions.assertThat(leaveRequestRepository.findAll()).hasSize(2);
    }

}
