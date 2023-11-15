package com.topets.api.domain.dto;

import java.util.List;

public record DataErrorResponse(String message, List<String> details) {
}
