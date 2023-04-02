package com.task.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@ToString(exclude = {"orderLines"})
@Entity
@Getter
@Setter
public class Goods extends BaseEntity {

    private String name;

    private BigDecimal price;

    @OneToMany(mappedBy = "goods")
    @JsonIgnore
    private List<OrderLine> orderLines;


    public Goods(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Goods(Long id, String name, BigDecimal price) {
        super(id);
        this.name = name;
        this.price = price;
    }
}
