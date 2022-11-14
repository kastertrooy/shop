package com.example.demo.entity.update;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter@Getter
public class UpdateItems {
    @NotBlank(message = "Order Id can not be empty or null")
    private Integer orderId;
    @NotBlank(message = "Product Id can not be empty or null")
    private Integer productId;
    @NotBlank(message = "Amount can not be empty or null")
    private Integer amount;
    @NotBlank(message = "Price can not be empty or null")
    private Double price;
}
