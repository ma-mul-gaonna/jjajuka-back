package com.mmgon.jjajuka.domain.vacancy.controller;

import com.mmgon.jjajuka.domain.auth.dto.LoginResponse;
import com.mmgon.jjajuka.domain.vacancy.exception.VacancyErrorCode;
import com.mmgon.jjajuka.domain.vacancy.exception.VacancyException;
import com.mmgon.jjajuka.domain.vacancy.service.VacancySseService;
import com.mmgon.jjajuka.global.enums.Authority;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacancySseController {

    private static final String SESSION_MEMBER_KEY = "loginMember";

    private final VacancySseService vacancySseService;

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(HttpSession session) {
        LoginResponse loginMember = (LoginResponse) session.getAttribute(SESSION_MEMBER_KEY);
        if (loginMember == null) {
            throw new VacancyException(VacancyErrorCode.UNAUTHORIZED);
        }

        if (loginMember.getAuthority() != Authority.ADMIN) {
            throw new VacancyException(VacancyErrorCode.FORBIDDEN);
        }

        return vacancySseService.subscribe(session.getId());
    }
}
