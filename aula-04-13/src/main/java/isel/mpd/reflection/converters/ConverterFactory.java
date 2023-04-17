package isel.mpd.reflection.converters;

import java.time.LocalDate;

public class ConverterFactory {

    public static DateConverter getDateConverter(String convName) {
        DateConverter localDateConverter = (obj) -> {
            LocalDate date = (LocalDate) obj;
            return java.sql.Date.valueOf(date);
        };

        if (convName.equals("LocalDateConverter")) return localDateConverter;
        return null;
    }
}
