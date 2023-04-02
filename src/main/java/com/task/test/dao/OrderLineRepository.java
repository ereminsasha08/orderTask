package com.task.test.dao;


import com.task.test.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
//    @Query(value = "select  new com.task.test.dto.OrderDto(ol.id, ol.orderId, ol.goodsId, o.client, o.address, o.date, ol.count , g.name ,  g.price)" +
//            " from OrderLine ol left join Goods g on ol.goodsId = g.id left join Order o on ol.orderId = o.id where ol.orderId = :ordersId ")
//    List<OrderDto> getOrderWithGoodsByIdOrders(Long ordersId);

    void deleteByOrderId(Long orderId);

}
