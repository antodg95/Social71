package it.digiulio.social71.web.api.v1.controllers;

import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.ValidationException;
import it.digiulio.social71.web.api.v1.dto.WhisperDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/whispers")
public class WhisperController implements ICrudRestController<WhisperDTO>{

    @Override
    public WhisperDTO create(WhisperDTO whisperDTO) throws ValidationException, BadServiceRequestException {
        return null;
    }

    @Override
    public WhisperDTO findById(Long id) throws ValidationException {
        return null;
    }

    @Override
    public WhisperDTO update(Long id, WhisperDTO whisperDTO) throws BadServiceRequestException, ValidationException {
        return null;
    }

    @Override
    public WhisperDTO delete(Long id) throws BadServiceRequestException {
        return null;
    }
}
