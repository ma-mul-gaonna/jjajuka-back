package com.mmgon.jjajuka.domain.auth.service;

import com.mmgon.jjajuka.domain.auth.dto.LoginRequest;
import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;

    public Member login(LoginRequest request) {
        Member member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!member.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        return member;
    }
}
