package com.task.test.service;


import com.task.test.dao.GoodsRepository;
import com.task.test.entity.Goods;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class GoodsServiceImp implements GoodsService {

    private final GoodsRepository goodsRepository;


    @Override
    public Goods getIdByName(String nameGoods) {
        return goodsRepository.findIdByNameIgnoreCase(nameGoods);
    }

    @Override
    public List<Goods> getAll() {
        return goodsRepository.findAll();
    }

    @Override
    public Goods getById(Long id) {
        Optional<Goods> byId = goodsRepository.findById(id);
        return byId.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public Goods create(Goods goods) {
        if (!goods.isNew())
            throw new RuntimeException("У нового заказа не может быть id");
        return goodsRepository.save(goods);
    }

    @Override
    @Transactional
    public Goods update(Long id, Goods goods) {
        if (id != null && !goodsRepository.existsById(id))
            throw new EntityNotFoundException("Товар не найдена");
        goods.setId(id);
        return goodsRepository.save(goods);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!goodsRepository.existsById(id))
            throw new EntityNotFoundException("Товар не найдена");
        goodsRepository.deleteById(id);
    }


}
