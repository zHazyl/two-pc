package com.tnh.twophasecommit.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment {

    private Long id;
    private Long uid;
    private BigDecimal total;

}
