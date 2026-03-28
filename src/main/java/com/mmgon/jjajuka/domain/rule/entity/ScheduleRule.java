package com.mmgon.jjajuka.domain.rule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedule_rule")
@Getter
@NoArgsConstructor
public class ScheduleRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rule_name", length = 50, nullable = false)
    private String ruleName;

    @Column(name = "rule_type", length = 50, nullable = false)
    private String ruleType;

    @Column(name = "rule_value", length = 50, nullable = false)
    private String ruleValue;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
