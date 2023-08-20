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
public class Purchase {
    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="deal_id")
    private Deal deal;

    @Id
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    private int amount;
    private double price;
}
