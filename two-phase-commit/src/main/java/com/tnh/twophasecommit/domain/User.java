package com.tnh.twophasecommit.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "user1")
public class User {

    @Id
    private Long id;
    private BigDecimal total;

}