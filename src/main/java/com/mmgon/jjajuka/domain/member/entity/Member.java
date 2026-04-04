package com.mmgon.jjajuka.domain.member.entity;

import com.mmgon.jjajuka.global.enums.Authority;
import com.mmgon.jjajuka.global.enums.EmploymentStatus;
import com.mmgon.jjajuka.global.enums.Skills;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(name = "login_id", length = 255, nullable = false, unique = true)
    private String loginId;

    @Column(length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    @Column(length = 20)
    private String position;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status")
    private EmploymentStatus employmentStatus;

    @Enumerated(EnumType.STRING)
    @Column
    private Skills skills;

    public static Member create(String name, String loginId, String password, Authority authority,
                                String position, String phoneNumber, LocalDate hireDate,
                                EmploymentStatus employmentStatus, Skills skills) {
        Member member = new Member();
        member.name = name;
        member.loginId = loginId;
        member.password = password;
        member.authority = authority;
        member.position = position;
        member.phoneNumber = phoneNumber;
        member.hireDate = hireDate;
        member.employmentStatus = employmentStatus;
        member.skills = skills;
        return member;
    }
}
