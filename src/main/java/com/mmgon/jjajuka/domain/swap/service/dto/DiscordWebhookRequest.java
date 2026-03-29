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
    private String content;
    private List<DiscordEmbed> embeds;
    private String username;
    private String avatar_url;
}