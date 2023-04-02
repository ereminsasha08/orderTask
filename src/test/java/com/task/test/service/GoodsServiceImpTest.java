package com.task.test.service;

import com.task.test.dao.GoodsRepository;
import com.task.test.dao.OrderLineRepository;
import com.task.test.entity.Goods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoodsServiceImpTest {

    @Mock
    private GoodsRepository goodsRepositoryMock;

    @Mock
    private OrderLineRepository orderLineRepository;

    @InjectMocks
    private GoodsServiceImp goodsService;

    private Goods testGoods;

    @BeforeEach
    void setUp() {
        testGoods = new Goods("Test Goods", new BigDecimal("10.0"));
    }

    @Test
    void getIdByName_ShouldReturnGoods() {
        when(goodsRepositoryMock.findIdByNameIgnoreCase(eq("Test Goods"))).thenReturn(testGoods);

        Goods result = goodsService.getIdByName("Test Goods");

        verify(goodsRepositoryMock).findIdByNameIgnoreCase("Test Goods");
        assertEquals(testGoods, result);
    }

    @Test
    void getAll_ShouldReturnListOfGoods() {
        List<Goods> expectedList = Arrays.asList(testGoods, new Goods(2L, "Test Goods", new BigDecimal("15.0")));
        when(goodsRepositoryMock.findAll()).thenReturn(expectedList);

        List<Goods> result = goodsService.getAll();

        verify(goodsRepositoryMock).findAll();
        assertEquals(expectedList, result);
    }

    @Test
    void getById_WhenGoodsExists_ShouldReturnGoods() {
        when(goodsRepositoryMock.findById(1L)).thenReturn(Optional.of(testGoods));

        Goods result = goodsService.getById(1L);

        verify(goodsRepositoryMock).findById(1L);
        assertEquals(testGoods, result);
    }

    @Test
    void getById_WhenGoodsDoesNotExist_ShouldThrowEntityNotFoundException() {
        when(goodsRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> goodsService.getById(1L));

        verify(goodsRepositoryMock).findById(1L);
    }

    @Test
    void create_WhenGoodsHasId_ShouldThrowRuntimeException() {
        testGoods.setId(1L);

        assertThrows(RuntimeException.class, () -> goodsService.create(testGoods));
    }


    @Test
    void update_WhenGoodsExists_ShouldReturnGoods() {
        when(goodsRepositoryMock.existsById(1L)).thenReturn(true);
        when(goodsRepositoryMock.save(testGoods)).thenReturn(testGoods);

        Goods result = goodsService.update(1L, testGoods);

        verify(goodsRepositoryMock).existsById(1L);
        verify(goodsRepositoryMock).save(testGoods);
        assertEquals(testGoods, result);
    }

    @Test
    void update_WhenGoodsDoesNotExist_ShouldThrowEntityNotFoundException() {
        when(goodsRepositoryMock.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> goodsService.update(1L, testGoods));

        verify(goodsRepositoryMock).existsById(1L);
    }
}