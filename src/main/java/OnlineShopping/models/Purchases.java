package OnlineShopping.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@IdClass(PurchaseId.class)
@Table(name = "purchases")
public class Purchases {
    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="deal_id")
    private Deals deal;

    @Id
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column
    private int amount;
    @Column
    private double price;
}
