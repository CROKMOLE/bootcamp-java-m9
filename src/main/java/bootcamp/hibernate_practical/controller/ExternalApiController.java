package bootcamp.hibernate_practical.controller;

import bootcamp.hibernate_practical.dto.ExternalApiResponse;
import bootcamp.hibernate_practical.service.ExternalService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external")
@Validated
public class ExternalApiController {

    private final ExternalService externalService;

    public ExternalApiController(ExternalService externalService) {
        this.externalService = externalService;
    }

    @GetMapping
    public ExternalApiResponse external() {
        return externalService.requestExternalApi();
    }
}
