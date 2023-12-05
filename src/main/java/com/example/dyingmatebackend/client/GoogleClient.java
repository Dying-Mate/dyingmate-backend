package com.example.dyingmatebackend.client;

import com.example.dyingmatebackend.user.params.GoogleInfoResponse;
import com.example.dyingmatebackend.user.params.GoogleTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class GoogleClient {

    @Value("${oauth.google.client-id}")
    private String clientId;

    @Value("${oauth.google.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    // 구글 서버에 access token 요청
    public String requestAccessToken(String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", "http://localhost:8080/user/google");
        body.add("code", authorizationCode);

        String requestUri = "https://oauth2.googleapis.com/token";

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<GoogleTokens> apiResponse = restTemplate.postForEntity(requestUri, request, GoogleTokens.class);
        GoogleTokens googleTokens = apiResponse.getBody();

        return googleTokens.getAccessToken();
    }

    // 구글 서버에 사용자 정보 요청
    public GoogleInfoResponse requestGoogleInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        String requestUri = "https://oauth2.googleapis.com/tokeninfo";

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);

        GoogleInfoResponse response = restTemplate.postForObject(requestUri, request, GoogleInfoResponse.class);

        return response;
    }
}
