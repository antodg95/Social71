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

        Converter<User, UserDTO> userResolver =
                context -> {
                    User user = context.getSource();
                    return modelMapper.map(user, UserDTO.class);
                };

        Converter<UserDTO, User> userDTOUserConverter =
                context -> {
                    UserDTO userDTO = context.getSource();
                    return modelMapper.map(userDTO, User.class);
                };

        modelMapper
                .typeMap(WhisperDTO.class, Whisper.class)
                .addMappings(
                        mapper -> {
                            mapper.skip(Whisper::setActive);
                            mapper.using(userDTOUserConverter)
                                    .map(WhisperDTO::getUser, Whisper::setUser);
                        }
                );

        modelMapper
                .typeMap(Whisper.class, WhisperDTO.class)
                .addMappings(
                        mapper -> {
                            mapper
                                    .using(userResolver)
                                    .map(Whisper::getUser, WhisperDTO::setUser);
                        }
                );
    }
}
