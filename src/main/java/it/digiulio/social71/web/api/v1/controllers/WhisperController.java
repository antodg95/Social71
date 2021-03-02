package it.digiulio.social71.web.api.v1.controllers;

import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.ValidationException;
import it.digiulio.social71.models.Whisper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/whispers")
public class WhisperController implements ICrudRestController<Whisper>{


    @Override
    public Whisper create(Whisper entity) throws ValidationException, BadServiceRequestException {
        return null;
    }

    @Override
    public Whisper findById(Integer id) throws ValidationException {
        return null;
    }

    @Override
    public Whisper update(Integer id, Whisper entity) throws BadServiceRequestException, ValidationException {
        return null;
    }

    @Override
    public Whisper delete(Integer id) throws BadServiceRequestException {
        return null;
    }
}
