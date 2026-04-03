package com.mmgon.jjajuka.domain.vacancy.event;

import com.mmgon.jjajuka.domain.vacancy.entity.Vacancy;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class VacancyCreatedEvent extends ApplicationEvent {
    private final Vacancy vacancy;

    public VacancyCreatedEvent(Object source, Vacancy vacancy) {
        super(source);
        this.vacancy = vacancy;
    }
}
