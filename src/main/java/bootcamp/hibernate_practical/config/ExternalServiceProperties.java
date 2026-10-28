package bootcamp.hibernate_practical.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.external")
public record ExternalServiceProperties(
        @NotBlank String apiUrl,
        @NotBlank String apiBearer
) {}