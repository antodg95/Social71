package it.digiulio.social71.web.api.v1.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "UserResponse", description = "User response model")
public class UserDTOResponse {

    private Long id;

    private String username;

    private String password;

    private String email;
}
