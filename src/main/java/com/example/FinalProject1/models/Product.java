package com.example.FinalProject1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private Long product_id;

    @Column
    private String product_name;
    @Column
    private String product_desc;
    @Column
    private int stock;
    @Column
    private double price;

}
