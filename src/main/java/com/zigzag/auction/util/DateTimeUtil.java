package com.zigzag.auction.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class DateTimeUtil {
    public static final long DEFAULT_LOT_DURATION_DAYS = 3;

    private DateTimeUtil() {
    }

    public static LocalDateTime getCurrentUtcLocalDateTime() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}
