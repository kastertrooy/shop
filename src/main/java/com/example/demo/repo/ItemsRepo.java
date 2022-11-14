package com.example.demo.repo;

import com.example.demo.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemsRepo extends JpaRepository<OrderItems,Integer> {
    Optional<OrderItems>findById(Integer id);
}
