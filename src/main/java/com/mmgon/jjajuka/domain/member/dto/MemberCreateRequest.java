package com.mmgon.jjajuka.domain.member.dto;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.global.enums.Authority;
import com.mmgon.jjajuka.global.enums.EmploymentStatus;
import com.mmgon.jjajuka.global.enums.Position;

import java.time.LocalDate;

public record MemberCreateRequest(
        String name,
        String loginId,
        String password,
        Authority authority,
        Position position,
        String phoneNumber,
        LocalDate hireDate,
        EmploymentStatus employmentStatus
) {
    public Member toEntity() {
        return Member.create(name, loginId, password, authority, position, phoneNumber, hireDate, employmentStatus);
    }
}
