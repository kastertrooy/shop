package com.example.demo.controller;

import com.example.demo.entity.OrderItems;
import com.example.demo.entity.dto.ItemsDto;
import com.example.demo.entity.update.UpdateItems;
import com.example.demo.servise.ProductItemsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/items")
public class ProductItemsController {
    private final ProductItemsService itemsService;

    public ProductItemsController(ProductItemsService itemsService) {
        this.itemsService = itemsService;
    }
    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody @Valid OrderItems items){
        ItemsDto dto = itemsService.create(items);
        return ResponseEntity.ok(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>update(@PathVariable("id")Integer id,
                                   @RequestBody UpdateItems items){
        return ResponseEntity.ok(itemsService.update(id,items));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable("id")Integer id){
        return ResponseEntity.ok(itemsService.delete(id));
    }
}
