package com.practice.date.demo.payload.response;

import com.practice.date.demo.annotation.ClientTimeZone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResponse {

    private Long id;

    private String name;

    private String email;

    private LocalDateTime notificationDate;

    private LocalDate localDate;

    private Date date;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}