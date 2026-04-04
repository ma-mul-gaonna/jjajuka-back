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

    @Column(nullable = false)
    private int minRestHours;

    @Column(nullable = false)
    private int maxConsecutiveDays;

    @Column(nullable = false)
    private int maxShiftsPerDay;

    @Column(nullable = false)
    private int requiredCount;


    @OneToMany(mappedBy = "scheduleRule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RuleCustom> ruleCustoms = new ArrayList<>();

    @Builder
    public ScheduleRule(int minRestHours, int maxConsecutiveDays,
                        int maxShiftsPerDay, int requiredCount) {
        this.minRestHours = minRestHours;
        this.maxConsecutiveDays = maxConsecutiveDays;
        this.maxShiftsPerDay = maxShiftsPerDay;
        this.requiredCount = requiredCount;
    }

    public void addRuleCustom(String customValue) {
        ruleCustoms.add(new RuleCustom(customValue, this));
    }

    public void update(int minRestHours, int maxConsecutiveDays,
                       int maxShiftsPerDay, int requiredCount) {
        this.minRestHours = minRestHours;
        this.maxConsecutiveDays = maxConsecutiveDays;
        this.maxShiftsPerDay = maxShiftsPerDay;
        this.requiredCount = requiredCount;
    }
}
