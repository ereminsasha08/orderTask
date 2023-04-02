package com.task.test.dto;

import com.task.test.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor

@Getter
@Setter
public class OrderDto {

    private Long id;

    private String client;

    private Date date;

    private String address;

    public OrderDto(Order orderDto) {
        this.id = orderDto.getId();
        this.client = orderDto.getClient();
        this.date = orderDto.getDate();
        this.address = orderDto.getAddress();
    }


}
