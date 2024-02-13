package com.practice.date.demo.entity;

import com.practice.date.demo.annotation.ClientTimeZone;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_at")
    @ClientTimeZone
    private LocalDateTime createdAt;
}
