package pro.hexa.backend.main.api.common.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class TimeUtils {
    public static final String HH_MM_SS = "hh.mm.ss";

    public static String toFormat(LocalTime localTime, String format){
        return localTime.format(DateTimeFormatter.ofPattern(format));
    }
}
