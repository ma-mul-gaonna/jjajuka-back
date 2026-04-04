package com.mmgon.jjajuka.domain.rule.service;

import com.mmgon.jjajuka.domain.rule.entity.RuleCustom;
import com.mmgon.jjajuka.domain.rule.repository.RuleCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RuleCustomService {

    private final RuleCustomRepository ruleCustomRepository;

    public List<RuleCustom> findAll() {
        return ruleCustomRepository.findAll();
    }

    public RuleCustom findById(Integer id) {
        return ruleCustomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RuleCustom not found: " + id));
    }

    @Transactional
    public void delete(Integer id) {
        RuleCustom ruleCustom = ruleCustomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RuleCustom not found: " + id));
        ruleCustomRepository.delete(ruleCustom);
    }
}
