package it.digiulio.social71.web.api.v1.mappings;

import it.digiulio.social71.configuration.ModelMapperConfigurer;
import it.digiulio.social71.models.Whisper;
import it.digiulio.social71.web.api.v1.dto.request.WhisperDTORequest;
import it.digiulio.social71.web.api.v1.dto.response.WhisperDTOResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WhisperDtoMapper implements ModelMapperConfigurer {
    @Override
    public void configure(ModelMapper modelMapper) {
        modelMapper
                .typeMap(WhisperDTORequest.class, Whisper.class)
                .addMappings(
                        mapper -> {
                            mapper.skip(Whisper::setCreatedOn);
                            mapper.skip(Whisper::setUser);
                            mapper.skip(Whisper::setActive);
                            mapper.skip(Whisper::setId);
                        }
                );
        modelMapper
                .typeMap(Whisper.class, WhisperDTOResponse.class);
    }
}
