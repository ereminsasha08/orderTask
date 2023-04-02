package com.task.test.dto;

import com.task.test.entity.Goods;
import com.task.test.entity.Order;
import com.task.test.entity.OrderLine;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderLineDto {

    private Long id;

    private Order order;
    private String name;

    private Integer count;


    public static OrderLine toOrder(OrderLineDto orderLineDto, Order order, Goods goods) {
        OrderLine orderLine = new OrderLine();
        orderLine.setId(orderLineDto.getId());
        orderLine.setCount(orderLineDto.getCount());
        orderLine.setOrder(order);
        orderLine.setGoods(goods);
        return orderLine;
    }

    public OrderLineDto(String name, Integer count) {

        this.name = name;
        this.count = count;
    }
}
