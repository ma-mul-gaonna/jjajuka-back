package com.mmgon.jjajuka.domain.schedulegroup.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedule_group")
@Getter
@NoArgsConstructor
public class ScheduleGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "year_month", length = 10)
    private String yearMonth;

    @Column(length = 255)
    private String reason;
}
