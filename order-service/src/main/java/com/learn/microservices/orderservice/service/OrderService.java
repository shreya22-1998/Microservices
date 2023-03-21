package com.learn.microservices.orderservice.service;

import com.learn.microservices.orderservice.dto.OrderLineItemsDto;
import com.learn.microservices.orderservice.dto.OrderRequest;
import com.learn.microservices.orderservice.model.Order;
import com.learn.microservices.orderservice.model.OrderLineItems;
import com.learn.microservices.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {


    @Autowired
    private OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest)
    {
        Order order=new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
      List<OrderLineItems> orderLineItemsList= orderRequest.getOrderLineItemsDtoList()
                .stream().map(this::mapToDto).collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItemsList);
        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
