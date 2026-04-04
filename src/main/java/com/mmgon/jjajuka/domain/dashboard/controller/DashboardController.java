package com.mmgon.jjajuka.domain.dashboard.controller;

import com.mmgon.jjajuka.domain.dashboard.dto.response.DashboardResponse;
import com.mmgon.jjajuka.domain.dashboard.service.DashboardService;
import com.mmgon.jjajuka.domain.dayoff.entity.Dayoff;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(dashboardService.getDashboard());
    }
}
