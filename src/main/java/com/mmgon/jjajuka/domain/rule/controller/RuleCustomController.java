package com.mmgon.dutyflow.domain.rule.controller;

import com.mmgon.dutyflow.domain.rule.entity.RuleCustom;
import com.mmgon.dutyflow.domain.rule.service.RuleCustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rule-customs")
@RequiredArgsConstructor
public class RuleCustomController {

    private final RuleCustomService ruleCustomService;

    @GetMapping
    public ResponseEntity<List<RuleCustom>> getAll() {
        return ResponseEntity.ok(ruleCustomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleCustom> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ruleCustomService.findById(id));
    }
}
