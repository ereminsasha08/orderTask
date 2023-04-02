package com.task.test.service;


import com.task.test.dto.OrderDto;
import com.task.test.entity.Order;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAll();

    Order getById(Long id);

    OrderDto create(Order order);

    OrderDto update(Long id, Order order);

    void deleteById(Long id);


}
