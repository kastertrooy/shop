package com.example.demo.controller;

import com.example.demo.entity.create.CreateOrder;
import com.example.demo.entity.dto.OrderDto;
import com.example.demo.entity.update.UpdateOrder;
import com.example.demo.servise.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService orderService) {
        this.service = orderService;
    }
    @PostMapping("/new")
    public ResponseEntity<?>newOrder(@RequestBody @Validated CreateOrder order){
        OrderDto result = service.create(order);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateOrder(@PathVariable("id")Integer id,
                                        @RequestBody @Validated UpdateOrder updateOrder){
        OrderDto result = service.update(id,updateOrder);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/deliverdate/{id}")
    public ResponseEntity<?>updateOrder(@PathVariable("id")Integer id,
                                        LocalDate date ){
        OrderDto result = service.deliverData(id,date);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/deliver/{id}")
    public ResponseEntity<?>deliverOrder(@PathVariable("id")Integer id){
        OrderDto result = service.updateToDeliver(id);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/payment/{id}")
    public ResponseEntity<?>paymentOrder(@PathVariable("id")Integer id){
        OrderDto result = service.updateToPayment(id);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/delete/{id}")
    public ResponseEntity<?>deleteOrder(@PathVariable("id")Integer id){
        String result = service.delete(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<?>getById(@PathVariable("id")Integer id){
        OrderDto result = service.getDtoById(id);
        return ResponseEntity.ok(result);
    }

}
