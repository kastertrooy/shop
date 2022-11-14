package com.example.demo.servise;

import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.create.CreateOrder;
import com.example.demo.entity.dto.OrderDto;
import com.example.demo.entity.update.UpdateOrder;
import com.example.demo.excaption.BadRequest;
import com.example.demo.repo.OrderRepo;
import com.example.demo.repo.ProductRepo;
import com.example.demo.type.OrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;

    public OrderService(
            OrderRepo orderRepo,
            ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public OrderDto create(CreateOrder dto) {
        Optional<Product>optionalProduct = productRepo.findById(dto.getProductId());
        if (optionalProduct.isEmpty()){
            throw new BadRequest("Product not found");
        }
        Order order = new Order();
        //todo setUserId
        order.setProductId(dto.getProductId());
        order.setDeliveryData(dto.getDeliveryData());
        order.setRequirement(dto.getRequirement());
        order.setContact(dto.getContact());
        order.setOrder_status(OrderStatus.CREATED);
        order.setAddress(dto.getAddress());
        order.setCreateAt(LocalDateTime.now());
        orderRepo.save(order);
        return getDto(order);
    }

    private OrderDto getDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setDeliveryData(order.getDeliveryData());
        dto.setRequirement(order.getRequirement());
        dto.setContact(order.getContact());
        dto.setOrder_status(order.getOrder_status());
        dto.setAddress(order.getAddress());
        dto.setCreateAt(order.getCreateAt());
        return dto;
    }
private Order findOrder (Integer id){
        Optional<Order>optionalOrder = orderRepo.findById(id);
        if (optionalOrder.isEmpty()){
            throw new BadRequest("Order not found");
        }
        return optionalOrder.get();
}
    public OrderDto update(Integer id, UpdateOrder updateOrder) {
       Order order = findOrder(id);
        if (updateOrder.getAddress() != null && !updateOrder.getAddress().isEmpty()){
            order.setAddress(updateOrder.getAddress());
        }
        if (updateOrder.getRequirement() != null && !updateOrder.getRequirement().isEmpty()){
            order.setRequirement(updateOrder.getRequirement());
        }
        if (updateOrder.getDeliveryData() != null && !updateOrder.getDeliveryData().
                isAfter(LocalDate.ofEpochDay(System.currentTimeMillis()))){
            order.setDeliveryData(updateOrder.getDeliveryData());
        }
        if (updateOrder.getContact() != null){
            order.setContact(updateOrder.getContact());
        }
        orderRepo.save(order);
        return getDto(order);
    }

    public OrderDto getDtoById(Integer id) {
        return getDto(findOrder(id));
    }

    public OrderDto updateToDeliver(Integer id) {
        Order order = findOrder(id);
        if (order.getOrder_status().equals(OrderStatus.DELIVERED)){
            throw new BadRequest("This order is delivered! ID: "+id);
        } else if (order.getOrder_status().equals(OrderStatus.CREATED)) {
            throw new BadRequest("This order has not yet been paid! ID: "+id);
        }
        order.setDeliveredAt(LocalDateTime.now());
        order.setOrder_status(OrderStatus.DELIVERED);
        orderRepo.save(order);
        return getDto(order);
    }

    public OrderDto updateToPayment(Integer id) {
        Order order = findOrder(id);
        if (order.getOrder_status().equals(OrderStatus.CREATED)) {
            order.setOrder_status(OrderStatus.PAID);
            orderRepo.save(order);
            return getDto(order);
        } else if (order.getOrder_status().equals(OrderStatus.DELIVERED)) {
            throw new BadRequest("This order is delivered! ID:"+id);

        }else throw new BadRequest("This order has not yet been paid! ID: "+id);
    }

    public String delete(Integer id) {
        Order order = findOrder(id);
        orderRepo.delete(order);
        return "Order delete";
    }

    public OrderDto deliverData(Integer id, LocalDate date) {
        Order order = findOrder(id);
        if (order.getDeliveredAt() != null){
            throw new BadRequest("This order is delivered! ID: "+id);
        }
        order.setDeliveryData(date);
        orderRepo.save(order);
        return getDto(order);
    }

    public boolean existOrder(Integer orderId) {
        Optional<Order>optionalOrder = orderRepo.findById(orderId);
        if (optionalOrder.isEmpty()){
            throw new BadRequest("Order by "+orderId+"Id not found");
        }
        return true;
    }
}
