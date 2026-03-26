package com.mmgon.dutyflow.domain.rule.entity;

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
}
