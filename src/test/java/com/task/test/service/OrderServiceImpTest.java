package com.task.test.service;

import com.task.test.dao.OrderLineRepository;
import com.task.test.dao.OrderRepository;
import com.task.test.dto.OrderDto;
import com.task.test.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImpTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderLineRepository orderLineRepository;

    @InjectMocks
    private OrderServiceImp orderService;

    @Test
    public void testGetAllOrders() {
        List<OrderDto> expectedOrders = Arrays.asList(new OrderDto(new Order()), new OrderDto(new Order()));
        when(orderRepository.getAll()).thenReturn(expectedOrders);
        List<OrderDto> actualOrders = orderService.getAll();
        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    public void testGetOrderById() {
        Order expectedOrder = new Order();
        expectedOrder.setId(1L);
        when(orderRepository.findById(expectedOrder.getId())).thenReturn(Optional.of(expectedOrder));
        Order actualOrder = orderService.getById(expectedOrder.getId());
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void testCreateOrder() {
        Order newOrder = new Order();
        when(orderRepository.save(newOrder)).thenReturn(newOrder);
        OrderDto createdOrder = orderService.create(newOrder);
        verify(orderRepository, times(1)).save(newOrder);
        assertNotNull(createdOrder);
        assertEquals(newOrder.getId(), createdOrder.getId());
    }

    @Test
    public void testUpdateOrder() {
        Long orderId = 1L;
        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        Order updatedOrder = new Order();
        updatedOrder.setId(orderId);
        updatedOrder.setClient("new customer name");
        when(orderRepository.existsById(orderId)).thenReturn(true);
        when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);
        OrderDto updatedOrderDto = orderService.update(orderId, updatedOrder);
        verify(orderRepository, times(1)).existsById(orderId);
        verify(orderRepository, times(1)).save(updatedOrder);
        assertNotNull(updatedOrderDto);
        assertEquals(updatedOrder.getId(), updatedOrderDto.getId());
        assertEquals(updatedOrder.getClient(), updatedOrderDto.getClient());
    }


    @Test
    public void testDeleteById() {
        Long id = 1L;
        doReturn(true).when(orderRepository).existsById(id);
        doNothing().when(orderLineRepository).deleteByOrderId(id);

        orderService.deleteById(id);

        verify(orderRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteByIdEntityNotFound() {
        Long id = 1L;
        doReturn(false).when(orderRepository).existsById(id);

        assertThrows(EntityNotFoundException.class, () -> orderService.deleteById(id));
    }
}