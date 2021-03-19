package it.digiulio.social71.configuration;

import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeterRegistry {

    @Bean
    public MeterRegistryCustomizer<io.micrometer.core.instrument.MeterRegistry> meterRegistryCustomizer() {
        return registry -> registry.config().commonTags("app", "social71");
    }
}
