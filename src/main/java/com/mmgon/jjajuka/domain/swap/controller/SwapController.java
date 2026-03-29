package com.mmgon.jjajuka.domain.swap.controller;

import com.mmgon.jjajuka.domain.auth.dto.LoginResponse;
import com.mmgon.jjajuka.domain.swap.controller.request.SwapCreateRequest;
import com.mmgon.jjajuka.domain.swap.controller.response.SwapCreateResponse;
import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.domain.swap.exception.SwapErrorCode;
import com.mmgon.jjajuka.domain.swap.exception.SwapException;
import com.mmgon.jjajuka.domain.swap.service.SwapService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SwapController {

    private static final String SESSION_MEMBER_KEY = "loginMember";

    private final SwapService swapService;

    @GetMapping("/swaps")
    public ResponseEntity<List<Swap>> getAll() {
        return ResponseEntity.ok(swapService.findAll());
    }

    @GetMapping("/swaps/{id}")
    public ResponseEntity<Swap> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(swapService.findById(id));
    }

    @PostMapping("/shift-swap")
    public ResponseEntity<SwapCreateResponse> createSwapRequest(
            @Valid @RequestBody SwapCreateRequest request,
            HttpSession session
    ) {
        LoginResponse loginMember = (LoginResponse) session.getAttribute(SESSION_MEMBER_KEY);
        if (loginMember == null) {
            throw new SwapException(SwapErrorCode.UNAUTHORIZED);
        }

        SwapCreateResponse response = swapService.createSwapRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
