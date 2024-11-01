package com.careerguideaiofficial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAiService {

    @Autowired
    private RestTemplate restTemplate;


    // The API URL is injected from the application.properties file using the @Value annotation
    @Value("${openai.api.url}")
    private String apiUrl;

    public String getAiResponse(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Object[]{
                Map.of("role", "system", "content", "You are a career coach and skills advisor."),
                Map.of("role", "user", "content", prompt)
        });

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        Map<String, Object> response = restTemplate.postForObject(apiUrl, request, Map.class);

        if (response != null && response.containsKey("choices")) {
            return ((Map<String, String>) ((Map<String, Object>) ((java.util.ArrayList<?>) response.get("choices")).get(0)).get("message")).get("content");
        }

        return "Sorry, I couldn't generate a response.";
    }
}