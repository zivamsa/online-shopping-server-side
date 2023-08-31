package OnlineShopping.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String title;
    @NotBlank
    private String description;

    @Min(0)
    private int stock;
    @Min(0)
    private double price;

    @Nullable
    private String imagePath;
}
