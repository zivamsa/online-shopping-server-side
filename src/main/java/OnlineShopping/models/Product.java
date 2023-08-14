package OnlineShopping.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("Id")
    private Long product_id;

    @Column
    @JsonProperty("Title")
    private String product_name;
    @Column
    @JsonProperty("Description")
    private String product_desc;
    @Column
    @JsonProperty("Stock")
    private int stock;
    @Column
    @JsonProperty("Price")
    private double price;

}
