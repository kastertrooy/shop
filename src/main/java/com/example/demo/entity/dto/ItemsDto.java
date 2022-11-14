package com.example.demo.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter@Getter
public class ItemsDto {
    private Integer orderId;
    private Integer productId;
    private Integer amount;
    private Double price;
    private LocalDateTime createAt;
}
