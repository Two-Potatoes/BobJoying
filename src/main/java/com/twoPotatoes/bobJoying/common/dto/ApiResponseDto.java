package com.twoPotatoes.bobJoying.common.dto;

import lombok.Getter;

@Getter
public class ApiResponseDto {
    private String message;

    /**
     * @param message Response Body에 응답할 message
     */
    public ApiResponseDto(String message) {
        this.message = message;
    }
}
