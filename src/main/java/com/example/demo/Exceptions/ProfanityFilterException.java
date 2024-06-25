package com.example.demo.Exceptions;

import com.example.demo.product.productValidation.ProfanityValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class ProfanityFilterException extends CustomBaseException{
    private final Logger logger = LoggerFactory.getLogger(ProfanityValidator.class);
    public ProfanityFilterException() {
        super(HttpStatus.NOT_FOUND,new SimpleResponse("Profanity Filter external service is down"));
        logger.error("Profanity filter is down exception thrown {}", getClass().getSimpleName());
    }
}
