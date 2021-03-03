package it.digiulio.social71.configuration;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class JacksonConfiguration {
    private final List<ModelMapperConfigurer> modelMapperConfigurers;

    @Autowired
    public JacksonConfiguration(List<ModelMapperConfigurer> dtoMappers) {
        this.modelMapperConfigurers = dtoMappers;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setFieldMatchingEnabled(true);

        for (ModelMapperConfigurer configurer : this.modelMapperConfigurers) {
            log.debug("Configuring ModelMapper using {}", configurer.getClass().getSimpleName());
            configurer.configure(modelMapper);
        }

        return modelMapper;
    }
}
