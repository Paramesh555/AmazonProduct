package com.example.demo.product.productValidation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
@AllArgsConstructor
public class ProfanityFilterAPIResponse {
    private String original;
    private String censored;
    private boolean has_profanity;

    public ProfanityFilterAPIResponse(boolean has_profanity){
        this.has_profanity =has_profanity;
    }
}
