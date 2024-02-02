package net.bot.crypto.application.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BigDecimalUtil {

    public static String formatBigDecimalToKRW(BigDecimal bigDecimal) {
        return String.format("%,d", bigDecimal.longValue());
    }
}
