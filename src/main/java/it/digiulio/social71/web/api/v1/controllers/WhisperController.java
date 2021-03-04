package it.digiulio.social71.web.api.v1.controllers;

import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.NotFoundException;
import it.digiulio.social71.exception.ValidationException;
import it.digiulio.social71.models.User;
import it.digiulio.social71.models.Whisper;
import it.digiulio.social71.service.WhisperService;
import it.digiulio.social71.web.api.v1.dto.UserDTO;
import it.digiulio.social71.web.api.v1.dto.WhisperDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/whispers")
public class WhisperController implements ICrudRestController<WhisperDTO>{

    private final WhisperService whisperService;
    private final ModelMapper modelMapper;

    @Override
    public WhisperDTO create(WhisperDTO whisperDTO) throws ValidationException, BadServiceRequestException {
        log.debug("POST: api/v1/whispers - create");
        log.trace("whisper: {}", whisperDTO);

        Whisper whisper = this.modelMapper.map(whisperDTO, Whisper.class);

        whisper = this.whisperService.create(whisper);

        return this.modelMapper.map(whisper, WhisperDTO.class);
    }

    @Override
    public WhisperDTO findById(Long id) throws ValidationException {
        log.debug("GET: api/v1/whispers/{id} - findById ");
        log.trace("id: {}", id);
        if (id < 0) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("must be greater than 0"));
        }

        Optional<Whisper> whisper = this.whisperService.findById(id);

        if (whisper.isEmpty()) {
            throw new NotFoundException(id.toString(), "User");
        }

        return this.modelMapper.map(whisper.get(), WhisperDTO.class);
    }

    @Override
    public WhisperDTO update(Long id, WhisperDTO whisperDTO) throws BadServiceRequestException, ValidationException {
        log.debug("PUT: api/v1/whispers/{id} - update");
        log.trace("id: {}, whisper:{}", id, whisperDTO);

        if (id < 0) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("must be greater than 0"));
        }

        whisperDTO.setId(id);
        Whisper whisper = this.modelMapper.map(whisperDTO, Whisper.class);
        whisper = this.whisperService.update(whisper);

        log.trace("updated user: {}", whisperDTO);
        return this.modelMapper.map(whisper, WhisperDTO.class);
    }

    @Override
    public WhisperDTO delete(Long id) throws BadServiceRequestException {
        log.debug("DELETE: api/v1/whispers/{id} - delete");
        log.trace("id: {}", id);

        if (id < 0) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("must be greater than 0"));
        }

        Whisper whisper = this.whisperService.delete(id);

        log.trace("deleted user: {}", whisper);
        return this.modelMapper.map(whisper, WhisperDTO.class);
    }
}
