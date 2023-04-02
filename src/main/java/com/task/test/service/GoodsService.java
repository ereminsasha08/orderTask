package com.task.test.service;


import com.task.test.entity.Goods;

import java.util.List;

public interface GoodsService {

    List<Goods> getAll();

    Goods getById(Long id);

    Goods create(Goods goods);

    Goods update(Long id, Goods goods);

    void deleteById(Long id);

    Goods getIdByName(String nameGoods);
}
