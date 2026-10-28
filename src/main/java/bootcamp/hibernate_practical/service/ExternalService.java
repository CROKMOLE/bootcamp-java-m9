package bootcamp.hibernate_practical.service;

import bootcamp.hibernate_practical.config.ExternalServiceProperties;
import bootcamp.hibernate_practical.dto.ExternalApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.Duration;
import java.util.Map;

@Service
public class ExternalService {


    private RestClient restClient;

    public ExternalService(RestClient.Builder builder, ExternalServiceProperties properties) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(2));  // max time to establish the connection
        factory.setReadTimeout(Duration.ofSeconds(3));

        this.restClient = builder.baseUrl(properties.apiUrl())
                .requestFactory(factory)
                .defaultHeader("Authorization", "Bearer " + properties.apiBearer())
                .build();
    }

    public ExternalApiResponse requestExternalApi() {
        long start = System.nanoTime();

        try {
            //common receiving response of the request
            RestClient.ResponseSpec spec = restClient.get().retrieve();

            //transform to readable response entity which contains data of the request
            ResponseEntity<String> entity = spec
                    .onStatus(HttpStatusCode::isError, (request, response) -> {
                    })
                    .toEntity(String.class);

            //get states of the response
            HttpStatusCode status = entity.getStatusCode();
            String reason = status instanceof HttpStatus hs ? hs.getReasonPhrase() : "";
            MediaType contentType = entity.getHeaders().getContentType();

            //transform received data to response DTO
            return new ExternalApiResponse(
                    status.value(),
                    reason,
                    entity.getHeaders().toSingleValueMap(),
                    contentType != null ? contentType.toString() : null,
                    entity.getBody(),
                    elapsedMs(start),
                    null);

        } catch (RestClientException e) {   // connection refused, timeout, DNS, etc.
            return new ExternalApiResponse(0, "", Map.of(), null, null, elapsedMs(start), e.getMessage());
        }
    }

    private long elapsedMs(long startNanos) {
        return (System.nanoTime() - startNanos) / 1_000_000;
    }
}