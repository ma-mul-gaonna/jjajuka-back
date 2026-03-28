package com.mmgon.jjajuka.domain.auth.controller;

import com.mmgon.jjajuka.domain.auth.dto.LoginRequest;
import com.mmgon.jjajuka.domain.auth.dto.LoginResponse;
import com.mmgon.jjajuka.domain.auth.service.AuthService;
import com.mmgon.jjajuka.domain.member.entity.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String SESSION_MEMBER_KEY = "loginMember";

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        Member member = authService.login(request);
        session.setAttribute(SESSION_MEMBER_KEY, new LoginResponse(member));
        return ResponseEntity.ok(new LoginResponse(member));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }
}
