package it.digiulio.social71.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public interface ModelMapperConfigurer {
    void configure(ModelMapper modelMapper);
}
