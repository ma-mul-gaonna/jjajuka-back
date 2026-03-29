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
public class DiscordWebhookRequest {
    private List<DiscordEmbed> embeds;
    private List<Component> components;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Component {
        private Integer type;
        private List<ActionButton> components;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActionButton {
        private Integer type;
        private Integer style;
        private String label;
        private String url;
    }
}
