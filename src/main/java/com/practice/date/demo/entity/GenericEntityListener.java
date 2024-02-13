package com.practice.date.demo.entity;

import com.practice.date.demo.interceptor.TimeZoneInterceptor;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;
import java.util.TimeZone;

public class GenericEntityListener {
    @PrePersist
    public void prePersist(BaseEntity entity) {
        if (entity instanceof UpdatableEntity) {
            UpdatableEntity updatableEntity = (UpdatableEntity) entity;
            LocalDateTime currentDateTime = convertToUTC(LocalDateTime.now(getClientTimeZone().toZoneId()));
            updatableEntity.setCreatedAt(currentDateTime);
            updatableEntity.setUpdatedAt(currentDateTime);
        }
    }

    @PreUpdate
    public void preUpdate(UpdatableEntity entity) {
        LocalDateTime currentDateTime = convertToUTC(LocalDateTime.now());
        entity.setUpdatedAt(currentDateTime);
    }

    private LocalDateTime convertToUTC(LocalDateTime localDateTime) {

        return localDateTime.atZone(getClientTimeZone().toZoneId())
                .withZoneSameInstant(TimeZoneInterceptor.getServerTimeZone().toZoneId())
                .toLocalDateTime();
    }

    private TimeZone getClientTimeZone() {
        return TimeZoneInterceptor.getClientTimeZone();
    }
}