package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
public class OrderItems {
    @NotBlank(message = "Order Id can not be empty or null")
    private Integer orderId;
    @NotBlank(message = "Product Id can not be empty or null")
    private Integer productId;
    @NotBlank(message = "Amount can not be empty or null")
    private Integer amount;
    @NotBlank(message = "Price can not be empty or null")
    private Double price;
    private LocalDateTime createAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
