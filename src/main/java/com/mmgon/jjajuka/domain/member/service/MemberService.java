package com.mmgon.jjajuka.domain.member.service;

import com.mmgon.jjajuka.domain.member.dto.MemberCreateRequest;
import com.mmgon.jjajuka.domain.member.dto.MemberResponse;
import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .toList();
    }

    public Member findById(Integer id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + id));
    }

    @Transactional
    public void delete(Integer id) {
        memberRepository.delete(findById(id));
    }

    @Transactional
    public MemberResponse create(MemberCreateRequest request) {
        if (memberRepository.findByLoginId(request.loginId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다: " + request.loginId());
        }
        return MemberResponse.from(memberRepository.save(request.toEntity()));
    }
}
