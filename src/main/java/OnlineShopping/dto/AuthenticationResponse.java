package OnlineShopping.dto;

import OnlineShopping.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private Role role;

    private String firstName;
    private String lastName;
    private String address;
    private String email;
}
