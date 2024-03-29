package com.learn.microservices.inventoryservice.repository;

import com.learn.microservices.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface InventoryRepository extends JpaRepository<Inventory,Long> {

   List<Inventory> findBySkuCode(List<String> skuCode);
}
