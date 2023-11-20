package com.topets.api.domain.dto;

import jakarta.validation.constraints.NotBlank;


public record DataRegisterCommonDetails(

        @NotBlank
        String name,
        @NotBlank
        String deviceId
) {
}
