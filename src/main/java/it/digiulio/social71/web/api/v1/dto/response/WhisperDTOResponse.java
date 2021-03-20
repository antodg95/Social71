package it.digiulio.social71.web.api.v1.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Schema(name = "WhisperResponse", description = "Whisper response model")
public class WhisperDTOResponse {

    @Nullable
    private Long id;

    @NonNull
    private String text;

    @NonNull
    private Timestamp createdOn;
}
