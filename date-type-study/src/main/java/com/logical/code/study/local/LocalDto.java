package com.logical.code.study.local;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class LocalDto {
    private LocalDateTime clientDate;
    private LocalDateTime serverDate;

    public LocalDto(LocalDateTime clientDate) {
        this.clientDate = clientDate;
        this.serverDate = LocalDateTime.now();
    }
}
