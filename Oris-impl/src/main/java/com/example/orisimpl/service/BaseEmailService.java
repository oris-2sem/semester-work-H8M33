package com.example.orisimpl.service;

import dto.response.MailReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class BaseEmailService implements EmailService{

    private final RestTemplate restTemplate = new RestTemplate();

    private final String url = "https://api.msndr.net/v1/email/messages";

    private final String bearer = "47d6465422c15db3813cd7faec1ccb34";

    @Override
    public MailReponse sendEmail(String to, String subject, String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(bearer);
        Map<String, Object> map = new HashMap<>();
        map.put("from_email", "itisntmyproblem@gmail.com");
        map.put("to", to);
        map.put("subject", subject);
        map.put("text", text);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<MailReponse> response = this.restTemplate.postForEntity(url, entity, MailReponse.class);

        // check response status code
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }
}
