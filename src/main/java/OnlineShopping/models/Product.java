package OnlineShopping.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private int stock;
    private double price;

    @Nullable
    private String imagePath;

    @OneToMany(mappedBy = "product")
    private List<UserProductInteraction> userInteractions;

}
