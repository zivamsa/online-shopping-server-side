package OnlineShopping.dto;

import OnlineShopping.models.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class PayloadUser {
    private Integer id;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    private String address;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;
}
