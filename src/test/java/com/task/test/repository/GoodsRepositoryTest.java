package com.task.test.repository;

import com.task.test.dao.GoodsRepository;
import com.task.test.entity.Goods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@Transactional
public class GoodsRepositoryTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    public void getById() {
        Goods goods = goodsRepository.findById(1L).orElse(null);
        assertThat(goods, notNullValue());
    }

    @Test
    public void getAll() {
        List<Goods> all = goodsRepository.findAll();
        assertThat(all, hasSize(10));
    }


    @Test
    public void getIdByName() {
        Goods goods = goodsRepository.findIdByNameIgnoreCase("утюг");
        assertThat(goods, notNullValue());
    }

    @Test
    public void create() {
        Goods goods = new Goods("Дверь", new BigDecimal("10000"));
        Goods save = goodsRepository.save(goods);
        assertThat(save.getName(), equalTo("Дверь"));
        assertThat(save.getId(), notNullValue());
    }

    @Test
    public void update() {
        Goods goods = new Goods(1L, "Дверь", new BigDecimal("10000"));
        Goods save = goodsRepository.save(goods);
        assertThat(save.getName(), equalTo("Дверь"));
        assertThat(save.getId(), equalTo(1L));
    }

    @Test
    public void deleteById() {
        goodsRepository.deleteById(1L);
        Assertions.assertThrows(RuntimeException.class, () -> goodsRepository.findById(1L).get());
    }
}
