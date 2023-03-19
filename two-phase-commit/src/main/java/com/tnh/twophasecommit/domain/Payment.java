package com.tnh.twophasecommit.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "payment")
public class Payment {

    @Id
    private Long id;
    private Long uid;
    private BigDecimal total;

}
