package com.task.test.service;


import com.task.test.dao.OrderLineRepository;
import com.task.test.dto.OrderLineDto;
import com.task.test.entity.Goods;
import com.task.test.entity.Order;
import com.task.test.entity.OrderLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderLineServiceImp implements OrderLineService {

    private final OrderLineRepository orderLineRepository;

    private final GoodsService goodsService;

    private final OrderService orderService;

    @Override
    @Transactional
    public OrderLine addOrderLine(Long orderId, OrderLineDto orderLineDto) {
        if (orderLineDto.getId() != null)
            throw new RuntimeException("У новой позиции не может быть id");
        Order order = orderService.getById(orderId);
        Goods goodsByName = goodsService.getIdByName(orderLineDto.getName());
        OrderLine orderLine = OrderLineDto.toOrder(orderLineDto, order, goodsByName);
        orderLineRepository.save(orderLine);
        return orderLine;
    }

    @Override
    @Transactional
    public OrderLine updateOrderLine(Long lineId, Long orderId, OrderLineDto orderLineDto) {
        if (!orderLineRepository.existsById(lineId))
            throw new RuntimeException("Сущность не найдена");
        Order order = orderService.getById(orderId);
        checkPositionInOrder(orderLineDto, order);
        Goods goodsByName = goodsService.getIdByName(orderLineDto.getName());
        OrderLine orderLine = OrderLineDto.toOrder(orderLineDto, order, goodsByName);
        orderLine.setId(lineId);
        orderLineRepository.save(orderLine);
        return orderLine;
    }

    private static void checkPositionInOrder(OrderLineDto orderLineDto, Order order) {
        order.getOrderLines().stream()
                .filter(
                        l -> Objects.equals(l.getId(), orderLineDto.getId())
                )
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Нет поозиции в заказе"));
    }


    @Override
    @Transactional
    public void deleteOrderLine(Long lineId) {
        if (!orderLineRepository.existsById(lineId))
            throw new RuntimeException("Сущность не найдена");
        orderLineRepository.deleteById(lineId);
    }


}
