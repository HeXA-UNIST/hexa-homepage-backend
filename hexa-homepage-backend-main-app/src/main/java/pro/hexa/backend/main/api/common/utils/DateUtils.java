package pro.hexa.backend.main.api.common.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class DateUtils {

    public static final String YYYY_MM_DD = "yyyy.MM.dd";

    public static String toFormat(LocalDateTime dateTime, String format) {
        return dateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime dateToLocalDateTime(Date date){
        LocalDateTime localDateTime = Instant.ofEpochMilli(date.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
        return localDateTime;
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime){
        Date date= Timestamp.valueOf(localDateTime);
        return date;
    }
}
