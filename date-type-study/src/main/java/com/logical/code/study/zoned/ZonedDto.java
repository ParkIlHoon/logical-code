package com.logical.code.study.zoned;

import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@ToString
public class ZonedDto {
    private ZonedDateTime clientDate;
    private ZonedDateTime serverDate;
    private String zoneId;

    public ZonedDto(ZonedDateTime clientDate) {
        this.clientDate = clientDate;
        this.serverDate = ZonedDateTime.now();
        this.zoneId = clientDate.getOffset().getId();
    }
}
