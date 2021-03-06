package it.digiulio.social71.web.api.v1.mappings;

import it.digiulio.social71.configuration.ModelMapperConfigurer;
import it.digiulio.social71.models.User;
import it.digiulio.social71.web.api.v1.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper implements ModelMapperConfigurer {
    @Override
    public void configure(ModelMapper modelMapper) {
        modelMapper
            .typeMap(UserDTO.class, User.class)
            .addMappings(
                mapper -> {
                    mapper.skip(User::setCreatedOn);
                    mapper.skip(User::setActive);
                    mapper.skip(User::setDeleteUuid);
                }
            );
    }
}
