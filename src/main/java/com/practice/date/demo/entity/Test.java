package com.practice.date.demo.entity;


import com.practice.date.demo.annotation.ClientTimeZone;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "test_timezone")
@EntityListeners(GenericEntityListener.class)
public class Test extends UpdatableEntity {

    private String name;

    private String email;

    @ClientTimeZone
    private LocalDateTime notificationDate;

    @ClientTimeZone
    private LocalDate localDate;

    @ClientTimeZone
    private Date date;
}