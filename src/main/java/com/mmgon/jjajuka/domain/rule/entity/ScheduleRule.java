package com.mmgon.jjajuka.domain.rule.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule_rule")
@Getter
@NoArgsConstructor
public class ScheduleRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private int minStaffPerShift;

    @Column(nullable = false)
    private int maxStaffPerShift;

    @Column(nullable = false)
    private int maxConsecutiveDays;


    @OneToMany(mappedBy = "scheduleRule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RuleCustom> ruleCustoms = new ArrayList<>();

    @Builder
    public ScheduleRule(String name, int minStaffPerShift, int maxStaffPerShift,
                        int maxConsecutiveDays) {
        this.name = name;
        this.minStaffPerShift = minStaffPerShift;
        this.maxStaffPerShift = maxStaffPerShift;
        this.maxConsecutiveDays = maxConsecutiveDays;
    }

    public void addRuleCustom(String customValue) {
        ruleCustoms.add(new RuleCustom(customValue, this));
    }

    public void update(String name, int minStaffPerShift, int maxStaffPerShift,
                       int maxConsecutiveDays) {
        this.name = name;
        this.minStaffPerShift = minStaffPerShift;
        this.maxStaffPerShift = maxStaffPerShift;
        this.maxConsecutiveDays = maxConsecutiveDays;
    }
}
