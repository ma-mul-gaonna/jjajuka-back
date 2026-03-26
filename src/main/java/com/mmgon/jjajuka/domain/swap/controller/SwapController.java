package com.mmgon.dutyflow.domain.swap.controller;

import com.mmgon.dutyflow.domain.swap.entity.Swap;
import com.mmgon.dutyflow.domain.swap.service.SwapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/swaps")
@RequiredArgsConstructor
public class SwapController {

    private final SwapService swapService;

    @GetMapping
    public ResponseEntity<List<Swap>> getAll() {
        return ResponseEntity.ok(swapService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Swap> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(swapService.findById(id));
    }
}
