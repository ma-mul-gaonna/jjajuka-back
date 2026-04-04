package com.mmgon.jjajuka.domain.member.controller;

import com.mmgon.jjajuka.domain.member.dto.MemberCreateRequest;
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

    @PostMapping
    public ResponseEntity<MemberResponse> create(@RequestBody MemberCreateRequest request) {
        return ResponseEntity.ok(memberService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(memberService.findById(id));
    }
}
