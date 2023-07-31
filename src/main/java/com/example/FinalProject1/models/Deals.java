package com.example.FinalProject1.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "deals")
public class Deals {
    @Id
    @GeneratedValue
    private Long deal_id;

    @ManyToOne
    @JoinColumn(name="username")
    private User user;

    public Deals(Long deal_id, User user) {
        this.deal_id = deal_id;
        this.user = user;
    }

    public Long getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(Long deal_id) {
        this.deal_id = deal_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
