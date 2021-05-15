package com.logical.code.study.local;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@ToString
public class LocalConvertDto {
    private LocalDateTime clientDate;
    private LocalDateTime serverDate;

    private ZonedDateTime convertedDate;
    private String zoneId;

    public LocalConvertDto(LocalDateTime clientDate, ZoneId zoneId) {
        this.clientDate = clientDate;
        this.serverDate = LocalDateTime.now();

        this.convertedDate = ZonedDateTime.of(clientDate, zoneId);
        this.zoneId = zoneId.getId();
    }
}
