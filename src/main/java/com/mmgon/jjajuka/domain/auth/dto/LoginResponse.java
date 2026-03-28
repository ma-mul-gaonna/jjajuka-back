package com.mmgon.jjajuka.domain.auth.dto;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.global.enums.Authority;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final Integer id;
    private final String name;
    private final String loginId;
    private final Authority authority;

    public LoginResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.loginId = member.getLoginId();
        this.authority = member.getAuthority();
    }
}
