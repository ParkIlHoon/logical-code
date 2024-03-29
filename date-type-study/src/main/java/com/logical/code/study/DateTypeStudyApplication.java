package com.logical.code.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class DateTypeStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DateTypeStudyApplication.class, args);
    }

    @PostConstruct
    public void postConstruct () {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
