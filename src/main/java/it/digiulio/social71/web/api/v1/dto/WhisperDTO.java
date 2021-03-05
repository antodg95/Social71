package it.digiulio.social71.web.api.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Schema(name = "Whisper", description = "Whisper model")
public class WhisperDTO {

    @Nullable
    private Long id;

    @NonNull
    private String text;

    private Timestamp createdOn;

    @NonNull
    private UserDTO user;
}
