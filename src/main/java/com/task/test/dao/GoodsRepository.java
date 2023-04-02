package com.task.test.dao;


import com.task.test.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    @Query("select g from Goods g where lower(g.name) = lower(:name)")
    Goods findIdByNameIgnoreCase(String name);
}
