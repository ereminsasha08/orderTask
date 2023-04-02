package com.task.test.service;


import com.task.test.dao.OrderLineRepository;
import com.task.test.dao.OrderRepository;
import com.task.test.dto.OrderDto;
import com.task.test.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderLineRepository orderLineRepository;


    @Override
    public List<OrderDto> getAll() {
        return orderRepository.getAll();
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Заказ не найден"));
    }


    @Override
    @Transactional
    public OrderDto create(Order order) {
        if (order.getId() != null)
            throw new RuntimeException("У нового заказа не может быть id");
        return new OrderDto(orderRepository.save(order));

    }

    @Override
    @Transactional
    public OrderDto update(Long id, Order order) {
        if (id != null && !orderRepository.existsById(id))
            throw new EntityNotFoundException("Заказ не найдена");
        order.setId(id);
        return new OrderDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!orderRepository.existsById(id))
            throw new EntityNotFoundException("Заказ не найдена");
        orderLineRepository.deleteByOrderId(id);
        orderRepository.deleteById(id);
    }

}
