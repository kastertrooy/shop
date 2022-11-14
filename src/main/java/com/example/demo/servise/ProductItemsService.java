package com.example.demo.servise;

import com.example.demo.entity.OrderItems;
import com.example.demo.entity.dto.ItemsDto;
import com.example.demo.entity.update.UpdateItems;
import com.example.demo.excaption.BadRequest;
import com.example.demo.repo.ItemsRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductItemsService {
    private final ProductService productService;
    private final OrderService orderService;
    private final ItemsRepo itemsRepo;

    public ProductItemsService(ProductService productService, OrderService orderService, ItemsRepo itemsRepo) {
        this.productService = productService;
        this.orderService = orderService;
        this.itemsRepo = itemsRepo;
    }

    public ItemsDto create(OrderItems items) {
        orderService.existOrder(items.getOrderId());
        productService.existProduct(items.getProductId());
        items.setCreateAt(LocalDateTime.now());
        items.setId(null);
        itemsRepo.save(items);
        return getDto(items);
    }

    public Object update(Integer id, UpdateItems items) {
        OrderItems oldItems = getItems(id);
        if (items.getProductId() != null){
            if (productService.existProduct(items.getProductId())){
                oldItems.setProductId(items.getProductId());
            }
        }
        if (items.getAmount() != null){
            oldItems.setAmount(items.getAmount());
        }
        if (items.getPrice() != null){
            oldItems.setPrice(items.getPrice());
        }
        if (items.getOrderId() != null){
            if (orderService.existOrder(items.getOrderId())){
                oldItems.setOrderId(items.getOrderId());
            }
        }
        itemsRepo.save(oldItems);
        return getDto(oldItems);
    }
    private OrderItems getItems(Integer id){
        Optional<OrderItems> orderItems = itemsRepo.findById(id);
        if (orderItems.isEmpty()){
            throw new BadRequest("Order items by "+id+" id not found");
        }
        return orderItems.get();
    }
    public ItemsDto getDto(Integer id) {
        return getDto(getItems(id));
    }
    public ItemsDto getDto(OrderItems items) {
        ItemsDto dto = new ItemsDto();
        dto.setProductId(items.getProductId());
        dto.setPrice(items.getPrice());
        dto.setAmount(items.getAmount());
        dto.setOrderId(items.getOrderId());
        dto.setCreateAt(items.getCreateAt());
        return dto;
    }

    public String delete(Integer id) {
        OrderItems items = getItems(id);
       itemsRepo.delete(items);
       return "Order Items delete";
    }
}
