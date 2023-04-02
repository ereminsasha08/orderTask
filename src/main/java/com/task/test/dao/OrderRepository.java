package com.task.test.dao;


import com.task.test.dto.OrderDto;
import com.task.test.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o ")
    List<OrderDto> getAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = "orderLines")
    Optional<Order> findById(Long id);

}
