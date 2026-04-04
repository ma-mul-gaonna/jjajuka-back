package com.mmgon.jjajuka.domain.schedule.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AiScheduleResponse {

    private String status;
    private String message;
    private List<Assignment> assignments;

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Assignment {
        private String date;
        private Integer userId;
        private String userName;
        private String shiftName;
        private String startTime;
        private String endTime;
    }
}