package com.example.FinalProject1.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(PurchaseId.class)
@Table(name = "purchases")
public class Purchases {
    @Id
    @ManyToOne
    @JoinColumn(name="deal_id")
    private Deals deal;

    @Id
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    private int amount;
    private int price;
}
