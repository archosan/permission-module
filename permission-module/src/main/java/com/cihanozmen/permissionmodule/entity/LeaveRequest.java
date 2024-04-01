package com.cihanozmen.permissionmodule.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cihanozmen.permissionmodule.enums.LeaveRequestStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(exclude = "requester")
@Table(name = "leave_requests")
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee requester;

    @Column(name = "leave_request_start_date")
    private LocalDate leaveRequestStartDate;

    @Column(name = "leave_request_end_date")
    private LocalDate leaveRequestEndDate;

    @Column(name = "leave_request_reason")
    private String leaveRequestReason;

    @Column(name = "given_leave_days")
    private Integer givenLeaveDays;

    @Builder.Default
    @Column(name = "leave_request_created_at")
    private LocalDate leaveRequestCreatedAt = LocalDate.now();

    private Integer approver_id;

    @Column(name = "leave_request_status")
    @Enumerated(EnumType.STRING)
    private LeaveRequestStatus status;
}
