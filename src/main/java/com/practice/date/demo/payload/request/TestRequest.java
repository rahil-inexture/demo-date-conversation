package com.practice.date.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestRequest {

    private String name;

    private String email;

    private LocalDateTime notificationDate;

    private LocalDate localDate;

    private Date date;
}
