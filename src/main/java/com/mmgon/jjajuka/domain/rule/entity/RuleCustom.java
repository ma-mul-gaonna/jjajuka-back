package com.mmgon.jjajuka.domain.rule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rule_custom")
@Getter
@NoArgsConstructor
public class RuleCustom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "custom_value", length = 255, nullable = false)
    private String customValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_rule_id", nullable = false)
    private ScheduleRule scheduleRule;

    public RuleCustom(String customValue, ScheduleRule scheduleRule) {
        this.customValue = customValue;
        this.scheduleRule = scheduleRule;
    }
}
