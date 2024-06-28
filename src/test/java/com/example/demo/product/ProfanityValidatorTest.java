package com.example.demo.product;

import com.example.demo.Exceptions.ProfanityFilterException;
import com.example.demo.Test1ApplicationTests;
import com.example.demo.product.productValidation.ProfanityFilterAPIResponse;
import com.example.demo.product.productValidation.ProfanityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Test1ApplicationTests.class)
public class ProfanityValidatorTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ProfanityValidator profanityValidator;

    @BeforeEach
    void setUp(){this.profanityValidator=new ProfanityValidator(restTemplate);}

    @Test
    void testHasProfanity_returnTrue(){
        ProfanityFilterAPIResponse response = new ProfanityFilterAPIResponse(true);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET),any(HttpEntity.class),eq(ProfanityFilterAPIResponse.class)))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        assertTrue(profanityValidator.hasProfanity("testName","testDescription"));
    }

    @Test
    void testHasNoProfanity_returnFalse(){
        ProfanityFilterAPIResponse response = new ProfanityFilterAPIResponse(false);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET),any(HttpEntity.class),eq(ProfanityFilterAPIResponse.class)))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        assertFalse(profanityValidator.hasProfanity("testName","testDescription"));
    }

    @Test
    void testHasProfanity_thrownException(){
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET),any(HttpEntity.class),eq(ProfanityFilterAPIResponse.class)))
                .thenThrow(new RuntimeException());

        ProfanityFilterException exception =  assertThrows(ProfanityFilterException.class,
                () ->profanityValidator.hasProfanity("testName","testDescription"));

        assertEquals("Profanity Filter external service is down",exception.getSimpleResponse().getMessage());

    }

}
