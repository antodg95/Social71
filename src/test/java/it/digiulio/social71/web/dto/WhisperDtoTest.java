package it.digiulio.social71.web.dto;

import it.digiulio.social71.configuration.JacksonConfiguration;
import it.digiulio.social71.models.User;
import it.digiulio.social71.models.Whisper;
import it.digiulio.social71.web.api.v1.dto.WhisperDTO;
import it.digiulio.social71.web.api.v1.mappings.WhisperDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
@Slf4j
public class WhisperDtoTest {

    private ModelMapper mm;

    private Whisper whisper;
    private User user;

    @BeforeEach
    public void setUp() {
        this.mm = new JacksonConfiguration(List.of(new WhisperDtoMapper())).modelMapper();

        this.user = new User();
        this.whisper = new Whisper();

        this.user.setId(1L);
        this.user.setUsername("username-test");
        this.user.setPassword("password-test");
        this.user.setEmail("email@test.com");
        this.user.setCreatedOn(Timestamp.from(Instant.now()));
        this.user.setActive(true);

        this.whisper.setId(1L);
        this.whisper.setText("whisper-test");
        this.whisper.setCreatedOn(Timestamp.from(Instant.now()));
        this.whisper.setActive(true);
        this.whisper.setUser(user);
    }

    @Test
    public void validateToDto() {
        this.mm.typeMap(Whisper.class, WhisperDTO.class).validate();
    }

    @Test
    public void validateFromDto() {
        this.mm.typeMap(WhisperDTO.class, Whisper.class).validate();
    }

    @Test
    public void testToDto() {
        WhisperDTO whisperDTO = this.mm.map(whisper, WhisperDTO.class);

        assertThat(whisperDTO.getId()).isEqualTo(whisper.getId());
        assertThat(whisperDTO.getText()).isEqualTo(whisper.getText());
        assertThat(whisperDTO.getCreatedOn()).isEqualTo(whisper.getCreatedOn());
        assertThat(whisperDTO.getUser().getId()).isEqualTo(whisper.getUser().getId());
        assertThat(whisperDTO.getUser().getUsername()).isEqualTo(whisper.getUser().getUsername());
        assertThat(whisperDTO.getUser().getEmail()).isEqualTo(whisper.getUser().getEmail());
        assertThat(whisperDTO.getUser().getPassword()).isEqualTo(whisper.getUser().getPassword());
    }

    @Test
    public void testFromDto() {
        WhisperDTO whisperDTO = this.mm.map(whisper, WhisperDTO.class);
        Whisper whisper1 = this.mm.map(whisperDTO, Whisper.class);

        assertThat(whisper1.getId()).isEqualTo(whisper.getId());
        assertThat(whisper1.getCreatedOn()).isEqualTo(whisper.getCreatedOn());
        assertThat(whisper1.getText()).isEqualTo(whisper.getText());
        assertThat(whisper1.getActive()).isNull();
        assertThat(whisper1.getUser().getUsername()).isEqualTo(whisper.getUser().getUsername());
        assertThat(whisper1.getUser().getEmail()).isEqualTo(whisper.getUser().getEmail());
        assertThat(whisper1.getUser().getDeleteUuid()).isNull();
        assertThat(whisper1.getUser().getCreatedOn()).isNull();
        assertThat(whisper1.getUser().getActive()).isNull();
    }
}
