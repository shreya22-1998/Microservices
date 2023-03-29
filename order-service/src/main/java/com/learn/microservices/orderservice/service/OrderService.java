package com.learn.microservices.orderservice.service;

import com.learn.microservices.orderservice.config.WebClientConfig;
import com.learn.microservices.orderservice.dto.InventoryResponse;
import com.learn.microservices.orderservice.dto.OrderLineItemsDto;
import com.learn.microservices.orderservice.dto.OrderRequest;
import com.learn.microservices.orderservice.model.Order;
import com.learn.microservices.orderservice.model.OrderLineItems;
import com.learn.microservices.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient webClient;

    public void placeOrder(OrderRequest orderRequest)
    {
        Order order=new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
      List<OrderLineItems> orderLineItemsList= orderRequest.getOrderLineItemsDtoList()
                .stream().map(this::mapToDto).collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodeList=order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList());

    //call inventory service
      InventoryResponse[] inventoryResponses= webClient.get()
                       .uri("http://loalhost:8082/api/inventory",
                               uriBuilder -> uriBuilder.queryParam("skuCode",skuCodeList).build())
                               .retrieve()
                                       .bodyToMono(InventoryResponse[].class)
                                               .block();

        boolean allProductsInStock=Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
        if(allProductsInStock) {
            orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Product not present"
            );
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
