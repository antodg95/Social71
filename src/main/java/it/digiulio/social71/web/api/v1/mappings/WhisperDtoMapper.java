package it.digiulio.social71.web.api.v1.mappings;

import it.digiulio.social71.configuration.ModelMapperConfigurer;
import it.digiulio.social71.models.User;
import it.digiulio.social71.models.Whisper;
import it.digiulio.social71.web.api.v1.dto.UserDTO;
import it.digiulio.social71.web.api.v1.dto.WhisperDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WhisperDtoMapper implements ModelMapperConfigurer {
    @Override
    public void configure(ModelMapper modelMapper) {
        modelMapper
                .typeMap(WhisperDTO.class, Whisper.class)
                .addMappings(
                        mapper -> {
                            mapper.skip(Whisper::setActive);
                            mapper.skip(Whisper::setUser);
                        }
                );
    }
}
