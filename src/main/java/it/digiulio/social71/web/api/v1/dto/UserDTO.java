package it.digiulio.social71.web.api.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@Schema(name = "User", description = "User model")
public class UserDTO {

    @Nullable
    private Long id;

    private String username;

    private String password;

    private String email;
}
