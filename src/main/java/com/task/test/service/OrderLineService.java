package com.task.test.service;


import com.task.test.dto.OrderLineDto;
import com.task.test.entity.OrderLine;

public interface OrderLineService {

    void deleteOrderLine(Long lineId);

    OrderLine addOrderLine(Long orderId, OrderLineDto orderLineDto);

    OrderLine updateOrderLine(Long lineId, Long orderId, OrderLineDto orderLineDto);
}
