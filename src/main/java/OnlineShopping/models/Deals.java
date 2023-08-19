package OnlineShopping.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "deals")
public class Deals {
    @Id
    @GeneratedValue
    private Long dealId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="username")
    private User user;

    @OneToMany(mappedBy = "deal", cascade = CascadeType.ALL)
    private List<Purchases> purchases;

}
