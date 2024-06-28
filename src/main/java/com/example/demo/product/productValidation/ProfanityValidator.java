package com.example.demo.product.productValidation;

import com.example.demo.Exceptions.ProfanityFilterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProfanityValidator {
    private static Logger logger = LoggerFactory.getLogger(ProfanityValidator.class);


    @Value("${profanity.api.key}")
    private String API_KEY;


    private RestTemplate restTemplate;

    public ProfanityValidator(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public boolean hasProfanity(String name, String description) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", API_KEY);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {

            ResponseEntity<ProfanityFilterAPIResponse> responseEntity = restTemplate
                    .exchange("https://api.api-ninjas.com/v1/profanityfilter?text=" + name + " " + description,
                            HttpMethod.GET,
                            entity, ProfanityFilterAPIResponse.class);


            logger.info("Profanity Validator-name and Description: {}", responseEntity.getBody());

            return (responseEntity.getBody().isHas_profanity());

        } catch (Exception ex) {
            logger.error("Error calling Profanity API", ex);
            throw new ProfanityFilterException();
        }
    }
}
