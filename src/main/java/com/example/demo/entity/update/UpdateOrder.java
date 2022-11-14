package com.example.demo.entity.update;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter@Getter
public class UpdateOrder {
    private LocalDate deliveryData;
    private String requirement;
    private String contact;
    private String address;
}
