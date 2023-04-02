package com.task.test.service;

import com.task.test.dao.OrderLineRepository;
import com.task.test.dto.OrderLineDto;
import com.task.test.entity.Goods;
import com.task.test.entity.Order;
import com.task.test.entity.OrderLine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderLineServiceImpTest {

    @Mock
    private OrderLineRepository orderLineRepository;

    @Mock
    private GoodsService goodsService;

    @Mock
    private OrderService orderService;
    @InjectMocks
    private OrderLineServiceImp orderLineService;


    @Test
    void testAddOrderLine() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        when(orderService.getById(orderId)).thenReturn(order);

        OrderLineDto orderLineDto = new OrderLineDto("test goods", 2);
        Goods goods = new Goods(1L, "test goods", new BigDecimal("10.0"));
        when(goodsService.getIdByName(eq("test goods"))).thenReturn(goods);

        OrderLine orderLine = new OrderLine();
        orderLine.setOrder(order);
        orderLine.setGoods(goods);
        orderLine.setCount(2);
        when(orderLineRepository.save(any(OrderLine.class))).thenReturn(orderLine);

        OrderLine result = orderLineService.addOrderLine(orderId, orderLineDto);

        assertNotNull(result);
    }

    @Test
    void testAddOrderLine_ThrowsException_WhenIdNotNull() {
        Long orderId = 1L;
        OrderLineDto orderLineDto = new OrderLineDto(1L, "test goods", 2);
        assertThrows(RuntimeException.class, () -> orderLineService.addOrderLine(orderId, orderLineDto));
    }

    @Test
    void testUpdateOrderLine() {

        Long lineId = 1L;
        Long orderId = 1L;
        Order order = new Order();

        order.setId(orderId);
        OrderLine e1 = new OrderLine();
        e1.setId(lineId);
        order.setOrderLines(List.of(e1));
        OrderLineDto orderLineDto = new OrderLineDto(1L, "test goods", 2);

        Goods goods = new Goods("test goods", new BigDecimal("10.0"));

        OrderLine orderLine = new OrderLine();
        orderLine.setId(lineId);
        orderLine.setOrder(order);
        orderLine.setGoods(goods);
        orderLine.setCount(2);
        when(orderLineRepository.existsById(any())).thenReturn(true);
        when(orderService.getById(any())).thenReturn(order);
        when(orderLineRepository.save(any(OrderLine.class))).thenReturn(orderLine);
        OrderLine result = orderLineService.updateOrderLine(lineId, orderId, orderLineDto);
        assertNotNull(result);

    }

    @Test
    void testDeleteOrderLine() {

        Long id = 1L;
        doReturn(true).when(orderLineRepository).existsById(id);
        orderLineService.deleteOrderLine(id);
        verify(orderLineRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteOrderLine_Exception() {
        Long id = 2L;
        doReturn(false).when(orderLineRepository).existsById(id);
        assertThrows(RuntimeException.class, () -> orderLineService.deleteOrderLine(id));
    }


}