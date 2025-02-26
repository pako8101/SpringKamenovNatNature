package kamenov.springkamenovnatnature.service.impl;

import kamenov.springkamenovnatnature.service.RecaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecaptchaServiceImpl implements RecaptchaService {

    @Value("${secret_key_recaptcha}")
    private String recaptchaSecret;

    private static final String RECAPTCHA_VERIFY_URL =
            "https://www.google.com/recaptcha/api/siteverify";
    @Override
    public boolean validateRecaptcha(String recaptchaResponse) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("secret", recaptchaSecret);
        params.put("response", recaptchaResponse);
        // Използваме POST заявка към API-то на Google
        RecaptchaResponse apiResponse = restTemplate.postForObject(
                RECAPTCHA_VERIFY_URL + "?secret={secret}&response={response}",
                null,
                RecaptchaResponse.class,
                params
        );
        return apiResponse != null && apiResponse.success;
    }

//
//        private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
//        private static final String SECRET_KEY = "${secret_key_recaptcha}"; // Заменете с вашия Secret Key
//
//        private final WebClient webClient;
//
//        public RecaptchaServiceImpl(WebClient.Builder webClientBuilder) {
//            this.webClient = webClientBuilder.baseUrl(RECAPTCHA_VERIFY_URL).build();
//        }
//@Override
//        public boolean validateRecaptcha(String recaptchaToken) {
//            Mono<RecaptchaResponse> responseMono = webClient.post()
//                    .uri(uriBuilder -> uriBuilder
//                            .queryParam("secret", SECRET_KEY)
//                            .queryParam("response", recaptchaToken)
//                            .build())
//                    .retrieve()
//                    .bodyToMono(RecaptchaResponse.class);
//
//            RecaptchaResponse response = responseMono.block();
//            return response != null && response.isSuccess();
//        }

        private static class RecaptchaResponse {
            private boolean success;
            private String challenge_ts;
            private String hostname;

            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }

            // Getters and Setters
        }
    }

