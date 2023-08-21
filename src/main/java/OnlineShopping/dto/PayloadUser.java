package OnlineShopping.dto;

import OnlineShopping.models.Role;
import OnlineShopping.validation.NullableNotBlank;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class PayloadUser {
    private Integer id;

    @NullableNotBlank
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @NullableNotBlank
    private String address;
    @NullableNotBlank
    private String firstName;
    @NullableNotBlank
    private String lastName;

    @NullableNotBlank
    @Enumerated(EnumType.STRING)
    private Role role;
}
