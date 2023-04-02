package com.task.test.controller;



import com.task.test.dto.OrderDto;
import com.task.test.dto.OrderLineDto;
import com.task.test.entity.Order;
import com.task.test.entity.OrderLine;
import com.task.test.service.OrderLineService;
import com.task.test.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(OrderController.REST_URL)
@RequiredArgsConstructor
public class OrderController {
    public static final String REST_URL = "/orders";

    private final OrderService orderService;

    private final OrderLineService orderLineService;

    @GetMapping
    public List<OrderDto> all() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return orderService.getById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody Order order) {
        return orderService.create(order);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto update(@PathVariable Long id, @RequestBody Order order) {
        return orderService.update(id, order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        orderService.deleteById(id);
    }



    @PostMapping("/{orderId}/positions")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderLine addOrderLine(@PathVariable Long orderId, @RequestBody OrderLineDto orderLineDto) {
        return orderLineService.addOrderLine(orderId, orderLineDto);
    }

    @PutMapping("/{orderId}/positions/{lineId}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderLine updateOrderLine(@PathVariable Long lineId, @PathVariable Long orderId, @RequestBody OrderLineDto orderLineDto) {
        return orderLineService.updateOrderLine(lineId, orderId, orderLineDto);
    }

    @DeleteMapping("/{}/positions/{lineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderLine(@PathVariable Long lineId) {
        orderLineService.deleteOrderLine(lineId);
    }

}
