package com.example.socksstoragerest.entity;


import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "socks_storage")
public class SocksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cottonPart;
    private Integer quantity;
    private String color;

    public void setColor(String color) {
            this.color = color.toLowerCase();
    }
}
