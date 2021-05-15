package com.logical.code.study.zoned;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@ToString
public class ZonedConvertDto {
    private ZonedDateTime clientDate;
    private ZonedDateTime serverDate;
    private String zoneId;

    private LocalDateTime convertDate;

    public ZonedConvertDto(ZonedDateTime clientDate) {
        this.clientDate = clientDate;
        this.serverDate = ZonedDateTime.now();
        this.zoneId = clientDate.getOffset().getId();

        this.convertDate = clientDate.toLocalDateTime();
    }
}
