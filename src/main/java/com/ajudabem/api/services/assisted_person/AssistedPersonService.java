package com.ajudabem.api.services.assisted_person;

import com.ajudabem.api.domains.assisted_person.AssistedPerson;
import com.ajudabem.api.domains.assisted_person.RiskLevel;
import com.ajudabem.api.domains.user.User;
import com.ajudabem.api.dto.assited_person.AssistedPersonRequestDTO;
import com.ajudabem.api.dto.assited_person.AssistedPersonResponseDTO;
import com.ajudabem.api.repositories.AssistedPersonRepository;
import com.ajudabem.api.services.user.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssistedPersonService {

    private final CurrentUserService currentUserService;
    private final AssistedPersonRepository repository;

    public AssistedPersonResponseDTO createAssistedPerson(AssistedPersonRequestDTO dto) {
        User user = currentUserService.get();

        AssistedPerson newAssistedPerson = new AssistedPerson();

        newAssistedPerson.setAuthor(user);

        newAssistedPerson.setFull_name(dto.full_name());
        newAssistedPerson.setAge(dto.age());
        newAssistedPerson.setGender(dto.gender());
        newAssistedPerson.setNotes(dto.notes());

        newAssistedPerson.setRiskLevel(RiskLevel.MEDIUM);

        newAssistedPerson.setStreet(dto.street());
        newAssistedPerson.setNumber(dto.number());
        newAssistedPerson.setNeighborhood(dto.neighborhood());
        newAssistedPerson.setCity(dto.city());
        newAssistedPerson.setState(dto.state());
        newAssistedPerson.setCountry(dto.country());

        repository.save(newAssistedPerson);

        return AssistedPersonResponseDTO.fromEntity(newAssistedPerson);
    }

    public AssistedPersonResponseDTO updateAssistedPerson(AssistedPersonRequestDTO dto, Long id) {

        AssistedPerson assistedPerson = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assisted person is not exists"));

        if (dto.full_name() != null) assistedPerson.setFull_name(dto.full_name());
        if (dto.age() != null) assistedPerson.setAge(dto.age());
        if (dto.gender() != null) assistedPerson.setGender(dto.gender());
        if (dto.notes() != null) assistedPerson.setNotes(dto.notes());
        if (dto.street() != null) assistedPerson.setStreet(dto.street());
        if (dto.number() != null) assistedPerson.setNumber(dto.number());
        if (dto.neighborhood() != null) assistedPerson.setNeighborhood(dto.neighborhood());
        if (dto.city() != null) assistedPerson.setCity(dto.city());
        if (dto.state() != null) assistedPerson.setState(dto.state());
        if (dto.country() != null) assistedPerson.setCountry(dto.country());

        repository.save(assistedPerson);

        return AssistedPersonResponseDTO.fromEntity(assistedPerson);
    }

    public List<AssistedPersonResponseDTO> getAll() {
        List<AssistedPerson> assistedPersonList = repository.findAll();

        return assistedPersonList.stream()
                .map(AssistedPersonResponseDTO::fromEntity)
                .toList();
    }

    public AssistedPersonResponseDTO getAssistedPerson(Long id) {
        AssistedPerson assistedPerson = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assisted person is not exists"));

        return AssistedPersonResponseDTO.fromEntity(assistedPerson);
    }

}
