package com.mmgon.jjajuka.domain.rule.controller;

import com.mmgon.jjajuka.domain.rule.entity.RuleCustom;
import com.mmgon.jjajuka.domain.rule.service.RuleCustomService;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ruleCustomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
