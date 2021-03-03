package it.digiulio.social71.web.api.v1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
public class UserDTO {

    @Nullable
    private Long id;

    private String username;

    private String password;

    private String email;
}
