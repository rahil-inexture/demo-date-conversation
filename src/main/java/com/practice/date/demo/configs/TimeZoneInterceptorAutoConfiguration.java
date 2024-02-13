package com.practice.date.demo.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.practice.date.demo.interceptor.TimeZoneInterceptor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Configuration
@ConditionalOnClass(WebMvcConfigurer.class)
public class TimeZoneInterceptorAutoConfiguration {
    private final static Logger logger = LoggerFactory.getLogger(TimeZoneInterceptor.class);

    @Autowired
    private CustomDateSerializer customDateSerializer;
    @Bean
    public TimeZoneInterceptor timeZoneInterceptor() {
        return new TimeZoneInterceptor();
    }
    @Bean
    public ModelMapper modelMapper() {return new ModelMapper(); }
    @Bean
    public ObjectMapper customObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule())
                .registerModule(new SimpleModule()
                .addSerializer(Date.class, new CustomDateSerializer())
                .addSerializer(LocalDateTime.class, new CustomDateSerializer())
                .addSerializer(LocalDate.class, new CustomDateSerializer())
                .addSerializer(Timestamp.class, new CustomDateSerializer())

                .addDeserializer(Date.class, new CustomDateDeserializer<Date>())
                .addDeserializer(LocalDateTime.class, new CustomDateDeserializer<LocalDateTime>())
                .addDeserializer(LocalDate.class, new CustomDateDeserializer<LocalDate>())
                .addDeserializer(Timestamp.class, new CustomDateDeserializer<Timestamp>()));

        return objectMapper;
    }

    @Configuration
    public class TimeZoneInterceptorConfig implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            logger.info("TimeZoneInterceptor is enabled");
            registry.addInterceptor(timeZoneInterceptor()).order(Ordered.HIGHEST_PRECEDENCE);
        }
    }
}