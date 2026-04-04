package com.mmgon.jjajuka.domain.schedule.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AiScheduleRequest {
    @JsonProperty("input_json")
    private InputJson inputJson;

    @JsonProperty("user_request")
    private List<String> userRequests;
}
