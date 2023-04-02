package com.task.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.test.entity.Goods;
import com.task.test.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GoodsController.class)
public class GoodsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GoodsService goodsService;


    @Test
    public void testGetAll() throws Exception {
        List<Goods> goodsList = Arrays.asList(
                new Goods("Product 1", new BigDecimal("10.00")),
                new Goods("Product 2", new BigDecimal("20.00"))
        );

        given(goodsService.getAll()).willReturn(goodsList);

        mockMvc.perform(get(GoodsController.REST_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Product 1")))
                .andExpect(jsonPath("$[0].price", is(10.00)))
                .andExpect(jsonPath("$[1].name", is("Product 2")))
                .andExpect(jsonPath("$[1].price", is(20.00)));
    }

    @Test
    public void testGetById() throws Exception {
        Long id = 1L;
        Goods goods = new Goods("Product 1", new BigDecimal("10.00"));

        given(goodsService.getById(id)).willReturn(goods);

        mockMvc.perform(get(GoodsController.REST_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Product 1")))
                .andExpect(jsonPath("$.price", is(10.00)));
    }

    @Test
    public void testCreate() throws Exception {
        Goods goods = new Goods("Product 1", new BigDecimal("10.00"));

        when(goodsService.create(any(Goods.class))).thenReturn(goods);
        mockMvc.perform(post(GoodsController.REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(goods)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Product 1")))
                .andExpect(jsonPath("$.price", is(10.00)));
    }


    @Test
    public void testUpdate() throws Exception {
        Long id = 1L;
        String name = "Product 1";
        BigDecimal price = BigDecimal.valueOf(10.0);
        Goods goods = new Goods("Product 1", new BigDecimal("10.0"));
        when(goodsService.update(eq(id), any(Goods.class))).thenReturn(goods);
        String requestBody = objectMapper.writeValueAsString(goods);
        mockMvc.perform(MockMvcRequestBuilders.put(GoodsController.REST_URL + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.price", is(price.doubleValue())));
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;
        doNothing().when(goodsService).deleteById(id);
        mockMvc.perform(MockMvcRequestBuilders.delete(GoodsController.REST_URL + "/{id}", id))
                .andExpect(status().isNoContent());
    }
}
