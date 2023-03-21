package com.learn.microservices.inventoryservice.service;

import com.learn.microservices.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class InventoryService {


    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly=true)
    public boolean isInStock(String skuCode){
        return inventoryRepository.findBySkuCode(skuCode).isPresent();

    }


}
