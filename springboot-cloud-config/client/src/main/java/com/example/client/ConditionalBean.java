package com.example.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "createBean", havingValue = "true", matchIfMissing = false)
public class ConditionalBean {

    public ConditionalBean() {
        System.out.println("Conditional Bean is Created");
    }
}
