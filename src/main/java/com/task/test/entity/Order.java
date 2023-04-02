package com.task.test.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity {

    private String client;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String address;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<OrderLine> orderLines;
}
