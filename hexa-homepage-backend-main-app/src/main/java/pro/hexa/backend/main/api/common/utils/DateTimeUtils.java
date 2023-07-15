package pro.hexa.backend.main.api.common.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtils {
    public static final String YYYY_MM_DD_HH_MM_ss_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String toFormat(LocalDateTime localDateTime, String format){
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }
}
