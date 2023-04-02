package com.task.test.controller;


import com.task.test.entity.Goods;
import com.task.test.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = GoodsController.REST_URL)
@RequiredArgsConstructor
public class GoodsController {
    public static final String REST_URL = "/goods";

    private final GoodsService goodsService;

    @GetMapping
    public List<Goods> all(){
        return goodsService.getAll();
    }

    @GetMapping("/{id}")
    public Goods getById(@PathVariable Long id){
        return goodsService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Goods create(@RequestBody Goods goods){
        return goodsService.create(goods);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Goods update(@PathVariable Long id, @RequestBody Goods goods){
        return goodsService.update(id, goods);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        goodsService.deleteById(id);
    }

}
