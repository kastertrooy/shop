package com.example.demo.entity.dto;

import com.example.demo.type.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class OrderDto {
    private Integer id;
    private LocalDate deliveryData;
    private String requirement;
    private String contact;
    private OrderStatus order_status;
    private String address;
    private LocalDateTime createAt;
}
