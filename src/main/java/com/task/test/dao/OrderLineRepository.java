package com.task.test.dao;


import com.task.test.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

    void deleteByOrderId(Long orderId);

}
