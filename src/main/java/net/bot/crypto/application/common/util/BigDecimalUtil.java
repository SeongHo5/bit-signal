package net.bot.crypto.application.common.util;

import java.math.BigDecimal;

public final class BigDecimalUtil {

    private BigDecimalUtil() {
        throw new IllegalStateException("유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

    public static String convertBigDecimalToWonFormat(BigDecimal bigDecimal) {
        return String.format("%,d", bigDecimal.longValue());
    }
}
