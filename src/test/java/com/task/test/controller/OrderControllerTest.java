package com.task.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.test.dto.OrderDto;
import com.task.test.dto.OrderLineDto;
import com.task.test.entity.Order;
import com.task.test.entity.OrderLine;
import com.task.test.service.OrderLineService;
import com.task.test.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderLineService orderLineService;


    @Test
    public void testGetAllOrders() throws Exception {
        List<OrderDto> orderDtoList = new ArrayList<>();
        OrderDto orderDto1 = new OrderDto();
        orderDto1.setId(1L);
        OrderDto orderDto2 = new OrderDto();
        orderDto2.setId(2L);
        orderDtoList.add(orderDto1);
        orderDtoList.add(orderDto2);
        when(orderService.getAll()).thenReturn(orderDtoList);
        mockMvc.perform(MockMvcRequestBuilders.get(OrderController.REST_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[1].id", is(2)));
    }

    @Test
    public void testGetOrderById() throws Exception {
        Long id = 1L;
        Order order = new Order();
        order.setId(id);
        when(orderService.getById(id)).thenReturn(order);
        mockMvc.perform(MockMvcRequestBuilders.get(OrderController.REST_URL + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())));
    }

    @Test
    public void testCreateOrder() throws Exception {
        Long id = 1L;
        Order order = new Order();
        order.setId(id);
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        when(orderService.create(any(Order.class))).thenReturn(orderDto);
        String requestBody = objectMapper.writeValueAsString(order);
        mockMvc.perform(MockMvcRequestBuilders.post(OrderController.REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(id.intValue())));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        Long id = 1L;
        Order orderToUpdate = new Order();
        orderToUpdate.setId(id);
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        when(orderService.update(eq(id), any(Order.class))).thenReturn(orderDto);
        String requestBody = objectMapper.writeValueAsString(orderToUpdate);
        mockMvc.perform(MockMvcRequestBuilders.put(OrderController.REST_URL + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(id.intValue())));
    }

    @Test
    public void testDeleteOrder() throws Exception {
        Long id = 1L;
        doNothing().when(orderService).deleteById(id);
        mockMvc.perform(delete(OrderController.REST_URL + "/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void addOrderLine() throws Exception {
        OrderLineDto orderLineDto = new OrderLineDto( "Product 1", 2);
        OrderLine value = new OrderLine();
        value.setId(1L);
        value.setCount(2);
        when(orderLineService.addOrderLine(eq(1L), any(OrderLineDto.class))).thenReturn(value);
        mockMvc.perform(post(OrderController.REST_URL + "/1" + "/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderLineDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.count").value(orderLineDto.getCount()))
                .andReturn();
    }

    @Test
    void updateOrderLine() throws Exception {
        OrderLineDto orderLine = new OrderLineDto( "Product 1", 2);
        OrderLineDto orderLineDto = new OrderLineDto( "Product 1", 2);
        OrderLine value = new OrderLine();
        value.setCount(2);
        when(orderLineService.updateOrderLine(eq(1L), eq(1L), any(OrderLineDto.class))).thenReturn(value);
        mockMvc.perform(put(OrderController.REST_URL + "/1" + "/positions/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderLineDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.count").value(orderLine.getCount()))
                .andReturn();
    }

    @Test
    void deleteOrderLine() throws Exception {
        mockMvc.perform(delete(OrderController.REST_URL + "/1" + "/positions/" + 1))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
