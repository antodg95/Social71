package it.digiulio.social71.web.api.v1.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Schema(name = "WhisperRequest", description = "Whisper request model")
public class WhisperDTORequest {

    @NonNull
    private String text;
}
