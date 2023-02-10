package pro.hexa.backend.main.api.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class DateUtils {

    public static final String YYYY_MM_DD = "yyyy.MM.dd";

    public static String toFormat(LocalDateTime dateTime, String format) {
        return dateTime.format(DateTimeFormatter.ofPattern(format));
    }
}
