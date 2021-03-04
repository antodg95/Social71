package it.digiulio.social71.web.api.v1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "Social71 API",
                version = "1.0",
                contact = @Contact(
                        url = "https://www.linkedin.com/in/antoniodigiulio95/",
                        name = "Antonio Di Giulio"
                )
        ),
        tags = {
                @Tag(name = "User", description = "Operations about Users "),
                @Tag(name = "Whisper", description = "Operations about Whispers")
        }
)
public class OpenApiDefinition {
}
