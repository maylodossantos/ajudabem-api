package com.ajudabem.api.controllers;

import com.ajudabem.api.dto.assited_person.AssistedPersonRequestDTO;
import com.ajudabem.api.dto.assited_person.AssistedPersonResponseDTO;
import com.ajudabem.api.dto.news.NewsRequestDTO;
import com.ajudabem.api.dto.news.NewsResponseDTO;
import com.ajudabem.api.services.assisted_person.AssistedPersonService;
import com.ajudabem.api.services.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assisted-person")
public class AssistedPersonController {

    private final AssistedPersonService assistedPersonService;

    @PostMapping
    public ResponseEntity<AssistedPersonResponseDTO> createAssistedPerson(@RequestBody AssistedPersonRequestDTO body) {
        return ResponseEntity.ok(assistedPersonService.createAssistedPerson(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssistedPersonResponseDTO> updateAssistedPerson(@RequestBody AssistedPersonRequestDTO body, @PathVariable Long id) {
        return ResponseEntity.ok(assistedPersonService.updateAssistedPerson(body, id));
    }

    @GetMapping
    public ResponseEntity<List<AssistedPersonResponseDTO>> getAllAssistedPerson() {
        return ResponseEntity.ok(assistedPersonService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssistedPersonResponseDTO> getAssistedPersonById(@PathVariable Long id) {
        return  ResponseEntity.ok(assistedPersonService.getAssistedPerson(id));
    }
}
