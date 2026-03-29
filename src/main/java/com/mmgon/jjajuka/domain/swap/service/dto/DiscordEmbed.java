package com.mmgon.jjajuka.domain.swap.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscordEmbed {
    private String title;
    private String description;
    private Integer color;
    private List<Field> fields;
    private Footer footer;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Field {
        private String name;
        private String value;
        private Boolean inline;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Footer {
        private String text;
    }
}
