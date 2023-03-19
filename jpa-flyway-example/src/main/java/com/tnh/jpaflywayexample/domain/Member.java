package com.tnh.jpaflywayexample.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "member")
public class Member {

    @Id
    private Long id;
    private String name;

}
