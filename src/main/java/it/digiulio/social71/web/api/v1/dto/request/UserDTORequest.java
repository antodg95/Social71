package it.digiulio.social71.web.api.v1.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@Schema(name = "UserRequest", description = "User request model")
public class UserDTORequest {

    @Nullable
    private String username;

    @Nullable
    private String password;

    @Nullable
    private String email;
}
