package com.practice.date.demo.configs;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.practice.date.demo.interceptor.TimeZoneInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;


@Component
public class CustomDateSerializer extends JsonSerializer<Object> {
    private static final Logger logger = LoggerFactory.getLogger(CustomDateSerializer.class);

    //@Value("${application.default.datetime.format:yyyy-MM-dd'T'HH:mm:ss.SSSSSS}")
    private String defaultDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";

    @Override
    public void serialize(Object dateObject, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (dateObject != null) {
            try {
                ZonedDateTime utcDateTime = convertToUtc(dateObject);

                TimeZone clientTimeZone = getClientZone();

                // Convert UTC ZonedDateTime to client's time zone
                ZonedDateTime clientDateTime = utcDateTime.withZoneSameInstant(clientTimeZone.toZoneId());

                // Use a custom formatter with fractional seconds
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(defaultDateFormat);
                gen.writeString(clientDateTime.format(formatter));

            } catch (Exception e) {
                logger.error("Error serializing date object: {}", dateObject, e);
                throw new IOException("Error serializing date object", e);
            }
        }
    }

    private ZonedDateTime convertToUtc(Object dateObject) {
        if (dateObject instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) dateObject;
            ZonedDateTime utcDateTime = localDateTime.atZone(TimeZoneInterceptor.getServerTimeZone().toZoneId());

            return utcDateTime;
        } else if (dateObject instanceof ZonedDateTime) {
            ZonedDateTime zonedDateTime = (ZonedDateTime) dateObject;
            return zonedDateTime;
        } else if (dateObject instanceof Date) {
            Instant instant = ((Date) dateObject).toInstant();
            return instant.atZone(TimeZoneInterceptor.getServerTimeZone().toZoneId());
        } else if (dateObject instanceof LocalDate) {
            LocalDate localDate = (LocalDate) dateObject;
            return localDate.atStartOfDay(TimeZoneInterceptor.getServerTimeZone().toZoneId());
        }
        logger.error("Unsupported date type for serialization: {}", dateObject);
        throw new IllegalArgumentException("Unsupported date type for serialization: " + dateObject);
    }

    private TimeZone getClientZone() {
        return TimeZoneInterceptor.getClientTimeZone();
    }
}