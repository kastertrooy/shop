package com.example.demo.controller;


import com.example.demo.entity.ProductImage;
import com.example.demo.entity.create.CreateProduct;
import com.example.demo.entity.dto.ProductDto;
import com.example.demo.entity.update.UpdateProduct;
import com.example.demo.servise.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody @Valid CreateProduct product){
        ProductDto dto = service.create(product);
       return ResponseEntity.ok(dto);
    }
    @PostMapping("/saveimage/{id}")
    public ResponseEntity<?>saveImage(@RequestParam MultipartFile file,
                                      @PathVariable("id")Integer productId){
        ProductImage result = service.saveImage(file,productId);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>update(@PathVariable("id")Integer id,
                                   @RequestBody UpdateProduct product){
        return ResponseEntity.ok(service.update(id,product));
    }
    @PutMapping("/visible/{id}")
    public ResponseEntity<?>visibleProduct(@PathVariable("id")Integer id){
        return ResponseEntity.ok(service.visible(id));
    }
    @PutMapping("/hidden/{id}")
    public ResponseEntity<?>hiddenProduct(@PathVariable("id")Integer id){
        return ResponseEntity.ok(service.visible(id));
    }
    @GetMapping("/getAll")
    public ResponseEntity<?>getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getById(@PathVariable("id")Integer id){
        return ResponseEntity.ok(service.getBuId(id));
    }

    @GetMapping("/admin/getAll")
    public ResponseEntity<?>getById(){
        return ResponseEntity.ok(service.getAllAdmin());
    }
}
