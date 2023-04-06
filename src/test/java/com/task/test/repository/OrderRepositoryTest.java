package com.task.test.repository;

import com.task.test.dao.OrderRepository;
import com.task.test.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@Transactional
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void getById() {
        Order order = orderRepository.findById(1L).orElse(null);
        assertThat(order, notNullValue());
    }

    @Test
    public void getAll() {
        List<Order> all = orderRepository.findAll();
        assertThat(all, hasSize(5));
    }


    @Test
    public void getIdById() {
        Order order = orderRepository.findById(1L).orElseGet(null);
        assertThat(order, notNullValue());
    }

    @Test
    public void create() {
        Order order = new Order();
        order.setClient("Оля");
        order.setAddress("Москва");
        order.setDate(new Date());
        Order save = orderRepository.save(order);
        assertThat(save.getClient(), equalTo("Оля"));
        assertThat(save.getId(), notNullValue());
    }

    @Test
    public void update() {
        Order order = new Order();
        order.setClient("Оля");
        order.setAddress("Москва");
        order.setDate(new Date());
        order.setId(1L);
        Order save = orderRepository.save(order);
        assertThat(save.getClient(), equalTo("Оля"));
        assertThat(save.getId(), equalTo(1L));
    }

    @Test
    public void deleteById() {
        orderRepository.deleteById(1L);
        Assertions.assertThrows(RuntimeException.class, () -> orderRepository.findById(1L).get());
    }
}
