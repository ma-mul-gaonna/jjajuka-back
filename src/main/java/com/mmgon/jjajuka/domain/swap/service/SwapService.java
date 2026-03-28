package com.mmgon.jjajuka.domain.swap.service;

import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.domain.swap.repository.SwapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SwapService {

    private final SwapRepository swapRepository;

    public List<Swap> findAll() {
        return swapRepository.findAll();
    }

    public Swap findById(Integer id) {
        return swapRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Swap not found: " + id));
    }
}
