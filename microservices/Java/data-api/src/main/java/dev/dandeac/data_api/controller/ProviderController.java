package dev.dandeac.data_api.controller;

import dev.dandeac.data_api.dtos.ProviderDTO;
import dev.dandeac.data_api.services.ProviderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/providers")
public class ProviderController {
    private final ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService){
        this.providerService = providerService;
    }

    @GetMapping()
    public ResponseEntity<List<ProviderDTO>> getProviders() {
        List<ProviderDTO> dtos = providerService.findProviders();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{providerId}")
    public ResponseEntity<?> getProvider(@PathVariable String providerId) {
        try {
            ProviderDTO dto = providerService.findProviderById(providerId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PostMapping()
    public ResponseEntity<?> addProvider(@Valid @RequestBody ProviderDTO providerDTO) {
        try {
            ProviderDTO dto = providerService.addProvider(providerDTO);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{providerId}")
    public ResponseEntity<String> deleteProvider(@PathVariable String providerId) {
        try {
            providerService.deleteProvider(providerId);
            return new ResponseEntity<>("Provider with id " + providerId + " was deleted.", HttpStatus.NO_CONTENT);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("/{providerId}")
    public ResponseEntity<?> updateProvider(@PathVariable String providerId,@Valid @RequestBody ProviderDTO providerDTO) {
        try {
            ProviderDTO dto = providerService.updateProvider(providerId, providerDTO);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteAllProviders() {
        providerService.deleteAllProviders();
        return new ResponseEntity<>("All providers were deleted.", HttpStatus.NO_CONTENT);
    }
}
