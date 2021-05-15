package com.logical.code.study.local;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
public class LocalController {


    @GetMapping("/local")
    public ResponseEntity getLocal(@RequestParam("date") LocalDateTime date) {

        System.out.println("========================");
        System.out.println(date.toString());
        System.out.println("========================");

        return ResponseEntity.ok(new LocalDto(date));
    }

    @GetMapping("/local/convert/UTC")
    public ResponseEntity getConvertUTC(@RequestParam("date") LocalDateTime date) {

        System.out.println("========================");
        System.out.println("before convert : " + date.toString());
        System.out.println("========================");

        return ResponseEntity.ok(new LocalConvertDto(date, ZoneId.of("UTC")));
    }

    @GetMapping("/local/convert/KST")
    public ResponseEntity getConvertKST(@RequestParam("date") LocalDateTime date) {

        System.out.println("========================");
        System.out.println("before convert : " + date.toString());
        System.out.println("========================");

        return ResponseEntity.ok(new LocalConvertDto(date, ZoneId.of("Asia/Seoul")));
    }

}
