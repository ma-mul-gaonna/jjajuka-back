package com.mmgon.jjajuka.domain.member.controller;

import com.mmgon.jjajuka.domain.member.dto.MemberResponse;
import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class
MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(memberService.findById(id));
    }
}
