package com.example.socksstoragerest.entity;

import jakarta.persistence.*;
import lombok.Data;

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


}
