package com.logical.code.study.zoned;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
public class ZonedController {

    @GetMapping("/zoned")
    public ResponseEntity getZoned(@RequestParam("date") ZonedDateTime date) {
        System.out.println("========================");
        System.out.println(date.toString());
        System.out.println("========================");

        return ResponseEntity.ok(new ZonedDto(date));
    }

    @GetMapping("/zoned/convert")
    public ResponseEntity getConvert(@RequestParam("date") ZonedDateTime date) {
        System.out.println("========================");
        System.out.println(date.toString());
        System.out.println("========================");

        return ResponseEntity.ok(new ZonedConvertDto(date));
    }
}
