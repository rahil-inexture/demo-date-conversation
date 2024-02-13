package com.practice.date.demo.entity;

import com.practice.date.demo.annotation.ClientTimeZone;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class UpdatableEntity extends BaseEntity {

    @Column(name = "updated_at")
    @ClientTimeZone
    private LocalDateTime updatedAt;
}
