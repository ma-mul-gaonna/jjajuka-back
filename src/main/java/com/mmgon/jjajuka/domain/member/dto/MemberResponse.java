package com.mmgon.jjajuka.domain.member.dto;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.global.enums.Authority;
import com.mmgon.jjajuka.global.enums.EmploymentStatus;
import com.mmgon.jjajuka.global.enums.Skills;

import java.time.LocalDate;

public record MemberResponse(
        Integer id,
        String name,
        Authority authority,
        String position,
        String phoneNumber,
        LocalDate hireDate,
        EmploymentStatus employmentStatus,
        Skills skills
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getAuthority(),
                member.getPosition(),
                member.getPhoneNumber(),
                member.getHireDate(),
                member.getEmploymentStatus(),
                member.getSkill()
        );
    }
}
