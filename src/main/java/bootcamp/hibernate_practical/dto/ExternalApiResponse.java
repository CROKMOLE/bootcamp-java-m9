package bootcamp.hibernate_practical.dto;

import java.util.Map;


public record ExternalApiResponse(
        int status,
        String reason,
        Map<String, String> headers,
        String contentType,
        String body,
        long durationMs,
        String error        // null on success, message on connection/timeout failures
) {}
