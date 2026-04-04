package com.mmgon.jjajuka.domain.member.dto;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.global.enums.Authority;
import com.mmgon.jjajuka.global.enums.EmploymentStatus;
import com.mmgon.jjajuka.global.enums.Grade;
import com.mmgon.jjajuka.global.enums.Position;

import java.time.LocalDate;

public record MemberResponse(
        Integer id,
        String name,
        Authority authority,
        Position position,
        Grade grade,
        String phoneNumber,
        LocalDate hireDate,
        EmploymentStatus employmentStatus
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getAuthority(),
                member.getPosition(),
                member.getPosition() != null ? member.getPosition().getGrade() : null,
                member.getPhoneNumber(),
                member.getHireDate(),
                member.getEmploymentStatus()
        );
    }
}
