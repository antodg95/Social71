package it.digiulio.social71.web.api.v1.controllers;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.digiulio.social71.exception.BadServiceRequestException;
import it.digiulio.social71.exception.NotFoundException;
import it.digiulio.social71.exception.ValidationException;
import it.digiulio.social71.models.Whisper;
import it.digiulio.social71.service.WhisperService;
import it.digiulio.social71.web.api.v1.dto.request.WhisperDTORequest;
import it.digiulio.social71.web.api.v1.dto.response.WhisperDTOResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/whispers")
public class WhisperController implements ICrudRestController<WhisperDTORequest, WhisperDTOResponse>{

    private final WhisperService whisperService;
    private final ModelMapper modelMapper;

    @Override
    @Operation(summary = "Create Whisper", tags = {"Whisper"}, responses = {
            @ApiResponse(responseCode = "200", description = "The Whisper", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = WhisperDTOResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Service Request Exception or Validation Exception")
    })
    @Timed(value = "whisper.create", description = "Time spent creating whispers", percentiles = {0.50, 0.75, 0.95, 0.98, 0.99, 0.999})
    public WhisperDTOResponse create(
            @RequestBody(description = "Whisper that needs to be created", required = true) WhisperDTORequest whisperDTO
    ) throws ValidationException, BadServiceRequestException {
        log.debug("POST: api/v1/whispers - create");
        log.trace("whisper: {}", whisperDTO);

        Whisper whisper = this.modelMapper.map(whisperDTO, Whisper.class);

        whisper = this.whisperService.create(whisper);

        return this.modelMapper.map(whisper, WhisperDTOResponse.class);
    }

    @Override
    @Operation(summary = "Find Whisper by ID", tags = {"Whisper"}, responses = {
            @ApiResponse(responseCode = "200", description = "The Whisper", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = WhisperDTOResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Service Request, id must be greater than 0"),
            @ApiResponse(responseCode = "404", description = "Whisper not found")
    })
    @Timed(value = "whisper.findById", description = "Time spent finding whispers by id", percentiles = {0.50, 0.75, 0.95, 0.98, 0.99, 0.999})
    public WhisperDTOResponse findById(
            @Parameter(description = "Whisper's Id that need to be fetched. Must be > 0", required = true) Long id
    ) throws ValidationException {
        log.debug("GET: api/v1/whispers/{id} - findById ");
        log.trace("id: {}", id);
        if (id < 0) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("must be greater than 0"));
        }

        Optional<Whisper> whisper = this.whisperService.findById(id);

        if (whisper.isEmpty()) {
            throw new NotFoundException(id.toString(), "Whisper");
        }

        return this.modelMapper.map(whisper.get(), WhisperDTOResponse.class);
    }

    @Override
    @Operation(summary = "Update Whisper", tags = {"Whisper"}, responses = {
            @ApiResponse(responseCode = "200", description = "The Whisper", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = WhisperDTOResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Service Request Exception or Validation Exception."),
    })
    @Timed(value = "whisper.update", description = "Time spent updating whispers", percentiles = {0.50, 0.75, 0.95, 0.98, 0.99, 0.999})
    public WhisperDTOResponse update(
            @Parameter(description = "Whisper's Id that need to be updated. Must be > 0", required = true) Long id,
            @RequestBody(description = "Whisper's fields that need to be updated", required = true) WhisperDTORequest whisperDTO
    ) throws BadServiceRequestException, ValidationException {
        log.debug("PUT: api/v1/whispers/{id} - update");
        log.trace("id: {}, whisper:{}", id, whisperDTO);

        if (id < 0) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("must be greater than 0"));
        }

        Whisper whisper = this.modelMapper.map(whisperDTO, Whisper.class);
        whisper.setId(id);
        whisper = this.whisperService.update(whisper);

        log.trace("updated user: {}", whisperDTO);
        return this.modelMapper.map(whisper, WhisperDTOResponse.class);
    }

    @Override
    @Operation(summary = "Delete Whisper", tags = {"Whisper"}, responses = {
            @ApiResponse(responseCode = "200", description = "The Whisper", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = WhisperDTORequest.class))),
            @ApiResponse(responseCode = "400", description = "Bad Service Request Exception: Whisper doesn't exist or Id < 0"),
    })
    @Timed(value = "whisper.delete", description = "Time spent deleting whispers", percentiles = {0.50, 0.75, 0.95, 0.98, 0.99, 0.999})
    public WhisperDTOResponse delete(
            @Parameter(description = "Whisper's Id that need to be deleted. Must be > 0", required = true) Long id
    ) throws BadServiceRequestException {
        log.debug("DELETE: api/v1/whispers/{id} - delete");
        log.trace("id: {}", id);

        if (id < 0) {
            throw new BadServiceRequestException("Id", id.toString(), List.of("must be greater than 0"));
        }

        Whisper whisper = this.whisperService.delete(id);

        log.trace("deleted user: {}", whisper);
        return this.modelMapper.map(whisper, WhisperDTOResponse.class);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all Whispers for a given user id", tags = {"Whisper"}, responses = {
            @ApiResponse(responseCode = "200", description = "List of all user's id Whispers", content = @Content(mediaType = "application/json",
                    schema = @Schema(allOf = WhisperDTOResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Service Request Exception: User doesn't exist or userId < 0")
    })
    @Timed(value = "whisper.findAllByUserId", description = "Time spent finding whispers by user id", percentiles = {0.50, 0.75, 0.95, 0.98, 0.99, 0.999})
    public ResponseEntity<Map<String, Object>> findAllByUserId(
            @Parameter(description = "User's Id to get his Whispers list", required = true) @PathVariable(name = "userId") Long userId
    ) throws BadServiceRequestException {
        log.debug("GET: api/v1/whispers/user/{userId} - findAllByUserId");
        log.trace("userId: {}", userId);
        if (userId < 0) {
            throw new BadServiceRequestException("Id", userId.toString(), List.of("must be greater than 0"));
        }

        List<Whisper> whisperList = this.whisperService.findAllByUserId(userId);
        List<WhisperDTOResponse> whisperDTOResponseList = whisperList
                .stream()
                .map(whisper -> this.modelMapper.map(whisper, WhisperDTOResponse.class))
                .collect(Collectors.toList());
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("resultSize", whisperDTOResponseList.size());
        returnMap.put("whispers", whisperDTOResponseList);
        return ResponseEntity.ok(returnMap);
    }
}
