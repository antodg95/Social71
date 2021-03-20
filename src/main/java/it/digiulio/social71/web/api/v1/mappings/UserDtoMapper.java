package it.digiulio.social71.web.api.v1.mappings;

import it.digiulio.social71.configuration.ModelMapperConfigurer;
import it.digiulio.social71.models.User;
import it.digiulio.social71.web.api.v1.dto.request.UserDTORequest;
import it.digiulio.social71.web.api.v1.dto.response.UserDTOResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper implements ModelMapperConfigurer {
    @Override
    public void configure(ModelMapper modelMapper) {
        modelMapper
            .typeMap(UserDTORequest.class, User.class)
            .addMappings(
                mapper -> {
                    mapper.skip(User::setCreatedOn);
                    mapper.skip(User::setActive);
                    mapper.skip(User::setDeleteUuid);
                    mapper.skip(User::setId);
                }
            );
        modelMapper
                .typeMap(User.class, UserDTOResponse.class);
    }
}
