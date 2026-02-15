package foot.ball.foot_api.tools;

import lombok.Data;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.Map;
import java.util.Optional;

@Data
public class SendRequest {
    private String apiKey;
    private String apiUrl;
    private HttpHeaders httpHeaders;
    private Map<String, String> body;
    private RestTemplate restTemplate;

    public SendRequest(String apiKey, String apiUrl, HttpHeaders httpHeaders) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.httpHeaders = httpHeaders;
        this.restTemplate = new RestTemplate();
    }

    public SendRequest(Map<String, String> body, String apiKey, String apiUrl, HttpHeaders httpHeaders) {
        this.body = body;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.httpHeaders = httpHeaders;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Envoie une requête GET
     */
    public Optional<ResponseEntity<String>> sendGetRequest() {
        try {
            // Ajouter l'API key aux headers si nécessaire
            if (apiKey != null && !apiKey.isEmpty()) {
                httpHeaders.set("Authorization", "Bearer " + apiKey);
            }

            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return Optional.of(response);
        } catch (RestClientException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Envoie une requête POST
     */
    public Optional<ResponseEntity<String>> sendPostRequest() {
        try {
            // Ajouter l'API key aux headers si nécessaire
            if (apiKey != null && !apiKey.isEmpty()) {
                httpHeaders.set("Authorization", "Bearer " + apiKey);
            }

            // S'assurer que le Content-Type est défini
            if (httpHeaders.getContentType() == null) {
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            }

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            return Optional.of(response);
        } catch (RestClientException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Méthode générique pour envoyer une requête avec n'importe quelle méthode HTTP
     */
    public Optional<ResponseEntity<String>> sendRequest(HttpMethod method) {
        try {
            // Ajouter l'API key aux headers si nécessaire
            if (apiKey != null && !apiKey.isEmpty()) {
                httpHeaders.set("Authorization", "Bearer " + apiKey);
            }

            HttpEntity<?> entity;
            if (method == HttpMethod.GET || method == HttpMethod.DELETE) {
                entity = new HttpEntity<>(httpHeaders);
            } else {
                if (httpHeaders.getContentType() == null) {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                }
                entity = new HttpEntity<>(body, httpHeaders);
            }

            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    method,
                    entity,
                    String.class
            );

            return Optional.of(response);
        } catch (RestClientException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}