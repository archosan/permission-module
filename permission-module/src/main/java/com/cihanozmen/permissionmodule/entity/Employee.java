package com.cihanozmen.permissionmodule.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.cihanozmen.permissionmodule.enums.EmployeeRole;
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
@Table(name = "employees")
@Getter
@Setter
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(exclude = "leaveRequests")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String name;

    @Column(name = "last_name")
    private String surname;

    private String email;

    @Column(name = "start_job_date")
    private LocalDate startJobDate;

    @Column(name = "used_leave_days")
    private Integer usedLeaveDays;

    @Column(name = "remaining_leave_days")
    private Integer remainingLeaveDays;

    @Column(name = "total_leave_days")
    private Integer totalLeaveDays;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private EmployeeRole role;

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LeaveRequest> leaveRequests;

    @PrePersist
    public void prePersist() {
        if (remainingLeaveDays == null) {
            remainingLeaveDays = 0;
        }
        if (totalLeaveDays == null) {
            totalLeaveDays = 0;
        }
        if (usedLeaveDays == null) {
            usedLeaveDays = 0;
        }
    }

}
