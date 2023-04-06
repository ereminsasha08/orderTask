package com.task.test.repository;

import com.task.test.dao.OrderLineRepository;
import com.task.test.entity.Goods;
import com.task.test.entity.Order;
import com.task.test.entity.OrderLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
public class OrderLineRepositoryTest {

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Test
    public void addOrderLine() {
        Order order = new Order();
        order.setId(1L);
        Goods goods = new Goods();
        goods.setId(1L);
        OrderLine orderLine = new OrderLine();
        orderLine.setOrder(order);
        orderLine.setGoods(goods);
        orderLine.setCount(2);
        OrderLine save = orderLineRepository.save(orderLine);
        assertNotNull(save.getId());
    }

    @Test
    public void updateOrderLine() {
        Order order = new Order();
        order.setId(1L);
        Goods goods = new Goods();
        goods.setId(1L);
        OrderLine orderLine = new OrderLine();
        orderLine.setId(1L);
        orderLine.setOrder(order);
        orderLine.setGoods(goods);
        orderLine.setCount(50);
        OrderLine save = orderLineRepository.save(orderLine);
        assertEquals(save.getCount(), 50);
    }

    @Test
    public void deleteOrderLine() {
        orderLineRepository.deleteByOrderId(1L);
        assertThrows(NoSuchElementException.class, () -> orderLineRepository.findById(1L).get());
    }
}
