package com.mmgon.jjajuka.domain.dayoff.service;

import com.mmgon.jjajuka.domain.dayoff.entity.Dayoff;
import com.mmgon.jjajuka.domain.dayoff.repository.DayoffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DayoffService {

    private final DayoffRepository dayoffRepository;

    public List<Dayoff> findAll() {
        return dayoffRepository.findAll();
    }

    public Dayoff findById(Integer id) {
        return dayoffRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dayoff not found: " + id));
    }
}
