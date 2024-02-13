package com.practice.date.demo.configs;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.practice.date.demo.interceptor.TimeZoneInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Component
public class CustomDateDeserializer<T> extends JsonDeserializer<T> {
    private static final Logger logger = LoggerFactory.getLogger(CustomDateDeserializer.class);

    private static final List<DateTimeFormatter> SUPPORTED_FORMATTERS = Arrays.asList(
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"),
            DateTimeFormatter.ISO_DATE_TIME,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ISO_DATE,
            DateTimeFormatter.ISO_LOCAL_DATE
    );

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateString = p.getValueAsString();
        String fieldName = p.getCurrentName();
        Object object = p.getCurrentValue();

        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            Class<?> fieldType = field.getType();

            if (dateString != null && !dateString.isEmpty()) {
                try {
                    for (DateTimeFormatter formatter : SUPPORTED_FORMATTERS) {
                        try {
                            TemporalAccessor temporalAccessor = formatter.parse(dateString);

                            if (ZonedDateTime.class.isAssignableFrom(fieldType)) {

                                // If the necessary fields are supported, parse as ZonedDateTime
                                LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);
                                ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, TimeZoneInterceptor.getServerTimeZone().toZoneId());

                                return (T) zonedDateTime;
                            } else if (LocalDateTime.class.isAssignableFrom(fieldType)) {

                                // If the necessary fields are supported, parse as LocalDateTime
                                LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);
                                LocalDateTime localUTCDateTime = convertLocalDateTimeToUTC(localDateTime).toLocalDateTime();

                                return (T) localUTCDateTime;
                            } else if (Date.class.isAssignableFrom(fieldType))  {
                                Instant instant = Instant.parse(dateString); // Assuming dateString is in ISO-8601 format
                                Date date = Date.from(instant);
                                return (T) date;
                            } else if (LocalDate.class.isAssignableFrom(fieldType)) {

                                // If the necessary fields are supported, parse as LocalDate
                                LocalDate localDate = LocalDate.from(temporalAccessor);

                                return (T) localDate;
                            }

                            // Continue trying other formatters
                        } catch (DateTimeParseException e) {
                            // Continue trying other formatters
                        }
                    }

                    //throw new IllegalArgumentException("Unsupported date string format: " + dateString);
                } catch (Exception e) {
                    logger.error("Error deserializing date string: {}", dateString, e);
                    throw new IOException("Error deserializing date string: " + dateString, e);
                }
            }
        } catch (NoSuchFieldException e) {
            logger.error("Error NoSuchFieldException : {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private ZonedDateTime convertLocalDateTimeToUTC(LocalDateTime localDateTime) {
        // Convert to ZonedDateTime with client time zone
        ZonedDateTime zonedDateTime = localDateTime.atZone(getClientZone().toZoneId());

        // Convert ZonedDateTime to UTC
        return zonedDateTime.withZoneSameInstant(TimeZoneInterceptor.getServerTimeZone().toZoneId());
    }

    private TimeZone getClientZone() {
        return TimeZoneInterceptor.getClientTimeZone();
    }
}